package com.kwambokaB.stockmarketapp.presentation.company_listings

import androidx.room.Query
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean  = false,
    val searchQuery: String = ""
)
