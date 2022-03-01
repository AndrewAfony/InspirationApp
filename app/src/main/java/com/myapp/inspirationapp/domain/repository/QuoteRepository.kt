package com.myapp.inspirationapp.domain.repository

import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.model.QuotesList
import com.myapp.inspirationapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun getRandomQuote(): Resource<Quote>

    suspend fun getQuotes(): Flow<Resource<QuotesList>>

}