package com.myapp.inspirationapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import com.myapp.inspirationapp.presentation.quotes_list_screen.QuotesState
import com.myapp.inspirationapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val repository: QuoteRepository
): ViewModel() {

    var quotes = MutableStateFlow(QuotesState())
    private set

    var randomQuote = MutableLiveData<Quote>()
        private set

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

    fun getSavedQuotes() = repository.getFavoriteQuotes()

    fun saveQuote(quote: Quote) = viewModelScope.launch {
        repository.saveQuote(quote)
    }

    fun deleteQuote(quote: Quote) = viewModelScope.launch {
        repository.deleteQuote(quote)
    }

}