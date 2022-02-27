package com.myapp.inspirationapp.domain.model

data class RandomQuote(
    val author: String,
    val content: String,
    val tags: List<String>
)
