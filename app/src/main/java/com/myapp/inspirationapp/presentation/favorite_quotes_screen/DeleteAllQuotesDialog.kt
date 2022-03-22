package com.myapp.inspirationapp.presentation.favorite_quotes_screen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteAllQuotesDialog(
    val deleteQuotes: () -> Unit
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage("Do you want to delete all quotes&")
                    .setPositiveButton("Yes"
                    ) { _, _ ->
                        deleteQuotes()
                    }
                    .setNegativeButton("No"
                    ) { _, _ -> }
                .create()

    }
