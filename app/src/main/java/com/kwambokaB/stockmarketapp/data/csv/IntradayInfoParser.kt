package com.kwambokaB.stockmarketapp.data.csv

import com.kwambokaB.stockmarketapp.data.api.dto.IntradayInfoDto
import com.kwambokaB.stockmarketapp.data.mapper.toIntraDayInfo
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import com.kwambokaB.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor():CSVParser<IntradayInfo
        > {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val cvsReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            cvsReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null

                    val dto = IntradayInfoDto(timestamp, close.toDouble()
                    )
                    dto.toIntraDayInfo()
                }
                .filter {
                    val yesterday = LocalDate.now().minusDays(1)
                    val filter = when (yesterday.dayOfWeek.value) {
                        6 -> yesterday.minusDays(1)
                        7 -> yesterday.minusDays(2)
                        else -> yesterday
                    }
                    it.date.dayOfMonth == filter.dayOfMonth
                }
                .sortedBy { it.date.hour}
                .also {
                    cvsReader.close()
                }
        }
    }
}