package com.myapp.inspirationapp.di

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapp.inspirationapp.BuildConfig
import com.myapp.inspirationapp.data.local.QuoteDatabase
import com.myapp.inspirationapp.data.remote.QuotesApi
import com.myapp.inspirationapp.data.repository.QuoteRepositoryImpl
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import com.myapp.inspirationapp.workers.WorkersDependencies
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

    @Provides
    @Singleton
    fun provideQuoteRepository(api: QuotesApi, db: QuoteDatabase): QuoteRepository {
        return QuoteRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "quote_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkerDependency(api: QuotesApi, db: QuoteDatabase): WorkersDependencies {
        return WorkersDependencies(
            api = api,
            db = db
        )
    }
}