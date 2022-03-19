package com.myapp.inspirationapp.presentation.search_quotes_screen

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.adapters.QuotesAdapter
import com.myapp.inspirationapp.databinding.FragmentSearchQuoteBinding
import com.myapp.inspirationapp.presentation.QuotesViewModel
import com.myapp.inspirationapp.utils.shareQuote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchQuoteFragment : Fragment() {

    private var _binding: FragmentSearchQuoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var quotesAdapter: QuotesAdapter

    private val viewModel: QuotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchQuoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.searchQuote.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                viewModel.onSearch(it.toString())
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.searchedQuotes.collect { quotes ->
                quotesAdapter.differ.submitList(quotes.searchedQuotes?.results)
            }
        }
    }

    private fun setupRecyclerView() {
        quotesAdapter = QuotesAdapter(
            onFavoriteClick = { quote ->
                viewModel.saveQuote(quote)
            },
            onShareClick = { text ->
                shareQuote(requireContext(), text)
            },
            onQuoteClick = {
                val action = SearchQuoteFragmentDirections.actionChangeQuoteFromSearch(it)
                view?.findNavController()?.navigate(action)
            }
        )
        binding.rvSearchedQuotes.apply {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}