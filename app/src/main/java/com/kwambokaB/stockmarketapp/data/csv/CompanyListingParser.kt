package com.kwambokaB.stockmarketapp.data.csv

import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import com.opencsv.CSVReader

@Singleton
class CompanyListingParser @Inject constructor():CSVParser<CompanyListing
        > {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val cvsReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            cvsReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    cvsReader.close()
                }
        }
    }
}