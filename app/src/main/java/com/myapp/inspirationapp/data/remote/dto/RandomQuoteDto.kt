package com.myapp.inspirationapp.data.remote.dto

import com.myapp.inspirationapp.domain.model.RandomQuote

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
    fun toRandomQuote(): RandomQuote = RandomQuote(author, content, tags)
}
