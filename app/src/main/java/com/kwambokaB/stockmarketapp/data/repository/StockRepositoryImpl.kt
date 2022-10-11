package com.kwambokaB.stockmarketapp.data.repository

import com.kwambokaB.stockmarketapp.data.api.StockApi
import com.kwambokaB.stockmarketapp.data.csv.CSVParser
import com.kwambokaB.stockmarketapp.data.localdb.StockDb
import com.kwambokaB.stockmarketapp.data.mapper.toCompanyListing
import com.kwambokaB.stockmarketapp.data.mapper.toCompanyListingEntity
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import com.kwambokaB.stockmarketapp.domain.repository.StockRepository
import com.kwambokaB.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDb,
    private val companyListingParser: CSVParser<CompanyListing>
): StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map{ it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try{
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load Data"))
                null
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't Load Data"))
                null
            }

            remoteListing?.let {
                listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map{ it.toCompanyListingEntity()}
                )

                emit(Resource.Success(
                   data = dao
                       .searchCompanyListing("")
                       .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))

            }
        }
    }
}