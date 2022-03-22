package com.myapp.inspirationapp.domain.repository

import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.model.QuotesList
import com.myapp.inspirationapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun getRandomQuote(): Resource<Quote>

    suspend fun getQuotes(): Flow<Resource<QuotesList>>

    suspend fun searchQuotes(query: String): Flow<Resource<QuotesList>>

    fun getFavoriteQuotes(): Flow<List<Quote>>

    suspend fun getQuoteById(id: String): Quote?

    suspend fun saveQuote(quote: Quote)

    suspend fun deleteQuote(quote: Quote)

    suspend fun deleteAllQuotes()
}