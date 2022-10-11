package com.kwambokaB.stockmarketapp.presentation.company_info

import com.kwambokaB.stockmarketapp.domain.model.CompanyInfo
import com.kwambokaB.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState (
  val stockInfo: List<IntradayInfo> = emptyList(),
  val company: CompanyInfo? = null,
  val isLoading: Boolean = false,
  val error: String? = null

)