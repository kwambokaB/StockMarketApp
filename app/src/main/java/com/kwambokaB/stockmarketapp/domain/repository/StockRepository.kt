package com.kwambokaB.stockmarketapp.domain.repository

import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import com.kwambokaB.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
      fetchFromRemote: Boolean,
      query: String
    ): Flow<Resource<List<CompanyListing>>>
}