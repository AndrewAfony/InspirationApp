package com.myapp.inspirationapp.data.remote.dto

import com.myapp.inspirationapp.domain.model.Quote

data class RandomQuoteDto(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    fun toQuote(): Quote = Quote(_id ,author, content, tags)
}
