package com.myapp.inspirationapp.domain.repository

import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.utils.Resource

interface QuoteRepository {

    suspend fun getRandomQuote(): Resource<Quote>
}