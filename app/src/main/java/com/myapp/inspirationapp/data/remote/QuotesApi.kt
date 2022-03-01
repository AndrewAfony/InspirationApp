package com.myapp.inspirationapp.data.remote

import com.myapp.inspirationapp.data.remote.dto.QuotesListDto
import com.myapp.inspirationapp.data.remote.dto.RandomQuoteDto
import retrofit2.http.GET

interface QuotesApi {

    @GET("/random")
    suspend fun getRandomQuote():RandomQuoteDto

    @GET("/quotes?page=1")
    suspend fun getQuotes(): QuotesListDto
}