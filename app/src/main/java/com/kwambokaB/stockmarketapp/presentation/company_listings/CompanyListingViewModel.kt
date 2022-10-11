package com.kwambokaB.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwambokaB.stockmarketapp.domain.repository.StockRepository
import com.kwambokaB.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel(){
    var state by mutableStateOf(CompanyListingsState())

    private  var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingEvents){
        when(event){
            is CompanyListingEvents.Refresh ->{
               getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingEvents.onSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                          result.data?.let { listing ->
                            state = state.copy(companies = listing)
                          }
                        }
                        is Resource.Error -> {
                             Unit
                        }
                        is Resource.Loading -> {
                           state = state.copy(isLoading = result.isLoading)
                        }
                    }

                }
        }
    }
}