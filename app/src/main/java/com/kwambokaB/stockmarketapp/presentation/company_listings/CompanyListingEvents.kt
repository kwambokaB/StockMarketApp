package com.kwambokaB.stockmarketapp.presentation.company_listings

sealed class CompanyListingEvents{
    object Refresh: CompanyListingEvents()
    data class onSearchQueryChange(val query: String): CompanyListingEvents()
}
