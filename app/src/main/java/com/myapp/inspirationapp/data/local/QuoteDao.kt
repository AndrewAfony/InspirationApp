package com.myapp.inspirationapp.data.local

import androidx.room.*
import com.myapp.inspirationapp.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM Quote")
    fun getQuotes(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)

}