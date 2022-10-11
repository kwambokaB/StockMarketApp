package com.kwambokaB.stockmarketapp.data.mapper

import com.kwambokaB.stockmarketapp.data.localdb.CompanyListingEntity
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
 return CompanyListing(
     name = name,
     symbol = symbol,
     exchange = exchange
 )
}
fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}