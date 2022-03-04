package com.myapp.inspirationapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey val _id: String,
    val author: String,
    val content: String,
    val tags: List<String>
)
