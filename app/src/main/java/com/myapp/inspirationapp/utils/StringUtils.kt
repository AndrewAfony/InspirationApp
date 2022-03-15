package com.myapp.inspirationapp.utils

import java.util.*

fun String.toCategory() : String {

    var result = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    result = result.substringBefore("-")

    return result
}