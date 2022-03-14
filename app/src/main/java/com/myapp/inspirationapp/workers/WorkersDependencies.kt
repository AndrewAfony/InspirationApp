package com.myapp.inspirationapp.workers

import com.myapp.inspirationapp.data.local.QuoteDatabase
import com.myapp.inspirationapp.data.remote.QuotesApi

data class WorkersDependencies(
    val api: QuotesApi,
    val db: QuoteDatabase
)