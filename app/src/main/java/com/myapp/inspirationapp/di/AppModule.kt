package com.myapp.inspirationapp.di

import com.myapp.inspirationapp.BuildConfig
import com.myapp.inspirationapp.data.remote.QuotesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuoteApi(): QuotesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.QUOTABLE_BASE_URL)
            .build()
            .create(QuotesApi::class.java)
    }
}