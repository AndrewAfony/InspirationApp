package com.myapp.inspirationapp.data.local

import androidx.room.TypeConverter

class QuoteConverters {

    @TypeConverter
    fun fromTag(tags: List<String>): String {
        return tags[0]
    }

    @TypeConverter
    fun toTag(tag: String): List<String> {
        return listOf(tag)
    }

}