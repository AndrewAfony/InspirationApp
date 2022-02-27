package com.myapp.inspirationapp.presentation.random_quote_screen

import androidx.lifecycle.ViewModel
import com.myapp.inspirationapp.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomQuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
): ViewModel() {


}