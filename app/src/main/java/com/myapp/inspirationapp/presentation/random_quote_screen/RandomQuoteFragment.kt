package com.myapp.inspirationapp.presentation.random_quote_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.databinding.FragmentRandomQuoteBinding
import com.myapp.inspirationapp.presentation.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        }

        binding.buttonLike.setOnClickListener {
            viewModel.randomQuote.value?.let { quote ->
                viewModel.saveQuote(quote)
            }
            Snackbar.make(it, "Saved", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    viewModel.randomQuote.value?.let { quote ->
                        viewModel.deleteQuote(quote)
                    }
                }
                .show()
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}