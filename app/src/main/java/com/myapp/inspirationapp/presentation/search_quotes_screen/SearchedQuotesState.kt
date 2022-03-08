package com.myapp.inspirationapp.presentation.search_quotes_screen

import com.myapp.inspirationapp.domain.model.QuotesList

data class SearchedQuotesState(
    val searchedQuotes: QuotesList? = null,
    val isLoading: Boolean = false
)