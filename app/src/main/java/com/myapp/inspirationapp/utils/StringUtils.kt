package com.myapp.inspirationapp.utils

import java.util.*

fun String.toCategory() : String {

    val result = this.uppercase(Locale.getDefault())

    if (result.contains("-")) {
        val first = result.substringBefore("")
        val second = result.substringAfter("")
        return "$first $second"
    }

    return result
}