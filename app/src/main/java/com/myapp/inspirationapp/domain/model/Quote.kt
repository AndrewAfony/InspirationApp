package com.myapp.inspirationapp.domain.model

data class Quote(
    val _id: String,
    val author: String,
    val content: String,
    val tags: List<String>
)
