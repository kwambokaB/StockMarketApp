package com.kwambokaB.stockmarketapp.data.repository

import com.kwambokaB.stockmarketapp.data.api.StockApi
import com.kwambokaB.stockmarketapp.data.localdb.StockDb
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import com.kwambokaB.stockmarketapp.domain.repository.StockRepository
import com.kwambokaB.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDb
): StockRepository {
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map{it.toCompanyLisiting()}
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try{
               val response = api.getListings()
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load Data"))
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't Load Data"))
            }
        }
    }
}