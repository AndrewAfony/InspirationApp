package com.myapp.inspirationapp.presentation.random_quote_screen

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.domain.model.RandomQuote
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import com.myapp.inspirationapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomQuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
): ViewModel() {

    var randomQuote = MutableLiveData<RandomQuote>()
    private set

    fun getQuote(view: View) {
        viewModelScope.launch {
            val result = repository.getRandomQuote()

            when (result) {
                is Resource.Success -> {
                    randomQuote.postValue(result.data!!)
                }
                is Resource.Error -> {
                    result.message?.let { Snackbar.make(view, it, Snackbar.LENGTH_LONG).show() }
                }
                else -> {}
            }
        }
    }

}