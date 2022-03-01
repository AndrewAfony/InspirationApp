package com.myapp.inspirationapp.presentation.quotes_list_screen

import com.myapp.inspirationapp.domain.model.QuotesList

data class QuotesState(
    val quotes: QuotesList? = null,
    val isLoading: Boolean = false
)
