package com.myapp.inspirationapp.utils

import android.content.Context
import android.content.Intent

fun shareQuote(context: Context, content: String?) {

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}