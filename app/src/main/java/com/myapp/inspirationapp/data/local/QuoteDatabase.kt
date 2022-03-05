package com.myapp.inspirationapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.inspirationapp.domain.model.Quote

@Database(entities = [Quote::class], version = 1)
@TypeConverters(QuoteConverters::class)
abstract class QuoteDatabase: RoomDatabase() {

    abstract val dao: QuoteDao
}