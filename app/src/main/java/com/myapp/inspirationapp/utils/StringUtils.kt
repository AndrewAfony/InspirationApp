package com.myapp.inspirationapp.utils

import java.util.*

fun String.toCategory() : String {

    val result = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    if (result.contains("-")) {
        val first = result.substringBefore("")
        val second = result.substringAfter("")
        return "$first $second"
    }

    return result
}