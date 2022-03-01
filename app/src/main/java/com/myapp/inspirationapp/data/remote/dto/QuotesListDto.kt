package com.myapp.inspirationapp.data.remote.dto

import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.domain.model.QuotesList

data class QuotesListDto(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Quote>,
    val totalCount: Int,
    val totalPages: Int
) {
    fun toQuotesList(): QuotesList {
        return QuotesList(count, lastItemIndex, page, results, totalCount, totalPages)
    }
}