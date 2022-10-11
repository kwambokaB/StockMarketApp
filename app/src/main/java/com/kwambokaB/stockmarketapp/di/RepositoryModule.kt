package com.kwambokaB.stockmarketapp.di


import com.kwambokaB.stockmarketapp.data.csv.CSVParser
import com.kwambokaB.stockmarketapp.data.csv.CompanyListingParser
import com.kwambokaB.stockmarketapp.data.csv.IntradayInfoParser
import com.kwambokaB.stockmarketapp.data.repository.StockRepositoryImpl
import com.kwambokaB.stockmarketapp.domain.model.CompanyListing
import com.kwambokaB.stockmarketapp.domain.model.IntradayInfo
import com.kwambokaB.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntraDayParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}