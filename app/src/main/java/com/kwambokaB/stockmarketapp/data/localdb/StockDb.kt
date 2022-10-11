package com.kwambokaB.stockmarketapp.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDb: RoomDatabase() {
    abstract val dao: StockDao
}