package com.myapp.inspirationapp.data.remote

import com.myapp.inspirationapp.data.remote.dto.RandomQuoteDto
import retrofit2.http.GET

interface QuotesApi {

    @GET("/random")
    suspend fun getRandomQuote():RandomQuoteDto
}