package com.myapp.inspirationapp.data.repository

import com.myapp.inspirationapp.data.remote.QuotesApi
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.model.QuotesList
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import com.myapp.inspirationapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val api: QuotesApi
): QuoteRepository {

    override suspend fun getRandomQuote(): Resource<Quote> {

        return try {
            val quote = api.getRandomQuote().toRandomQuote()
            Resource.Success(data = quote)
        } catch (e: HttpException) {
            Resource.Error(message = e.localizedMessage ?: "Unknown error")
        } catch (e: IOException) {
            Resource.Error(message = e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getQuotes(): Flow<Resource<QuotesList>> = flow {

        emit(Resource.Loading())

        try {
            val quotes = api.getQuotes().toQuotesList()
            emit(Resource.Success(data = quotes))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown error"))
        }
    }
}