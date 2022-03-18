package com.myapp.inspirationapp.presentation.random_quote_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.databinding.FragmentRandomQuoteBinding
import com.myapp.inspirationapp.presentation.QuotesViewModel
import com.myapp.inspirationapp.utils.shareQuote
import com.myapp.inspirationapp.utils.toCategory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RandomQuoteFragment : Fragment() {

    private val viewModel: QuotesViewModel by activityViewModels()

    private var _binding: FragmentRandomQuoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomQuoteBinding.inflate(inflater, container, false)

        viewModel.randomQuote.observe(viewLifecycleOwner) {
            binding.quote.text = it?.content
            binding.author.text = it?.author
            binding.tag.text = it?.tags?.get(0)?.toCategory()
        }

        binding.buttonLike.setOnClickListener {
            viewModel.randomQuote.value?.let { quote ->
                val quoteToSave = quote.copy(_id = UUID.randomUUID().toString())
                viewModel.saveQuote(quoteToSave)
            }

            Snackbar.make(it, "Saved", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    viewModel.randomQuote.value?.let { quote ->
                        viewModel.deleteQuote(quote)
                    }
                }
                .show()
        }

        binding.buttonShare.setOnClickListener {
            shareQuote(requireContext(), viewModel.randomQuote.value?.content)
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}