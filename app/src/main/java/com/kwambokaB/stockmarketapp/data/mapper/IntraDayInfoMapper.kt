package com.kwambokaB.stockmarketapp.data.mapper

import com.kwambokaB.stockmarketapp.data.api.dto.CompanyInfoDto
import com.kwambokaB.stockmarketapp.data.api.dto.IntradayInfoDto
import com.kwambokaB.stockmarketapp.domain.model.CompanyInfo
import com.kwambokaB.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntraDayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo{
    return  CompanyInfo(
    symbol = symbol ?: "",
    description = description ?: "",
    name =  name ?: "",
    country = country ?: "",
    industry = industry ?: ""
    )
}