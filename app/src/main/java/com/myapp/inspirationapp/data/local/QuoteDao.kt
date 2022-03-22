package com.myapp.inspirationapp.data.local

import androidx.room.*
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM Quote WHERE _id NOT LIKE :id")
    fun getFavoriteQuotes(id: String = Constants.RANDOM_WORKER_QUOTE_ID): Flow<List<Quote>>

    @Query("SELECT * FROM Quote WHERE _id LIKE :id")
    suspend fun getQuoteById(id: String): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)

    @Query("DELETE FROM Quote")
    suspend fun deleteAllQuotes()

}