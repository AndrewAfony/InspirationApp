package com.myapp.inspirationapp.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import com.myapp.inspirationapp.presentation.quotes_list_screen.QuotesState
import com.myapp.inspirationapp.presentation.search_quotes_screen.SearchedQuotesState
import com.myapp.inspirationapp.utils.Constants
import com.myapp.inspirationapp.utils.Resource
import com.myapp.inspirationapp.workers.RandomQuoteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "ViewModel"

@HiltViewModel
class QuotesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: QuoteRepository
): ViewModel() {

    private val workManager = WorkManager.getInstance(context)
    private val workInfo: Flow<WorkInfo> = workManager.getWorkInfosByTagLiveData("random_quote_work").asFlow().map { it[0] }

    var quotes = MutableStateFlow(QuotesState())
        private set

    var searchedQuotes = MutableStateFlow(SearchedQuotesState())
        private set

    var randomQuote = MutableLiveData<Quote?>()
        private set
    var randomQuoteIsLoading: Boolean = true

    var showSplashScreen = true

    init {
        loadRandomQuote()

        viewModelScope.launch {

            workInfo.collect { info ->
                if(info.state == WorkInfo.State.SUCCEEDED) {
                    val quote = repository.getQuoteById(Constants.RANDOM_WORKER_QUOTE_ID)
                    randomQuote.postValue(quote)
                } else if (info.state == WorkInfo.State.RUNNING || info.state == WorkInfo.State.ENQUEUED){
                    val quote = repository.getQuoteById(Constants.RANDOM_WORKER_QUOTE_ID)
                    randomQuote.postValue(quote)
                    Log.d(TAG, "Current state: ${info.state}")
                } else {
                    Log.d(TAG, "Current state: ${info.state}")
                    Log.d(TAG, "Error: ${info.outputData.getString("error")}")
                }
            }

        }

        viewModelScope.launch {
            delay(1000L)
            showSplashScreen = false
        }
    }

    fun loadQuotes() {
        viewModelScope.launch {
            repository.getQuotes()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            quotes.value = quotes.value.copy(
                                quotes = result.data
                            )
                        }
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {
                            quotes.value = quotes.value.copy(
                                quotes = result.data,
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun getRandomQuote() {
        viewModelScope.launch {
            val result = repository.getRandomQuote()

            when (result) {
                is Resource.Success -> {
                    randomQuote.postValue(result.data!!)
                }
                is Resource.Error -> {
//                    result.message?.let { Snackbar.make(view, it, Snackbar.LENGTH_LONG).show() }
                }
                else -> {}
            }
        }
    }

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            repository.searchQuotes(query)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            searchedQuotes.value = searchedQuotes.value.copy(
                                searchedQuotes = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {}
                        is Resource.Loading -> {
                            searchedQuotes.value = searchedQuotes.value.copy(
                                searchedQuotes = result.data,
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }


    }

    fun getSavedQuotes() = repository.getFavoriteQuotes()

    fun saveQuote(quote: Quote) = viewModelScope.launch {
        repository.saveQuote(quote)
    }

    fun deleteQuote(quote: Quote) = viewModelScope.launch {
        repository.deleteQuote(quote)
    }

    private fun loadRandomQuote() {

        val request = PeriodicWorkRequestBuilder<RandomQuoteWorker>(15, TimeUnit.MINUTES)
            .addTag("random_quote_work")
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            Constants.UNIQUE_RANDOM_QUOTE_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

}