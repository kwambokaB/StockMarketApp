package com.kwambokaB.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import com.kwambokaB.stockmarketapp.data.api.StockApi
import com.kwambokaB.stockmarketapp.data.localdb.StockDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): StockApi{
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDb(app:Application): StockDb{
        return  Room.databaseBuilder(
            app,
            StockDb::class.java,
            "stockdb.db"
        ).build()
    }
}