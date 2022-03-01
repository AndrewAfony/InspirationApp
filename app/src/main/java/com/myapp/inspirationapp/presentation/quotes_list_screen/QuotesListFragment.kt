package com.myapp.inspirationapp.presentation.quotes_list_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.inspirationapp.adapters.QuotesAdapter
import com.myapp.inspirationapp.databinding.FragmentQuotesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuotesListFragment : Fragment() {

    private var _binding: FragmentQuotesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var quotesAdapter: QuotesAdapter

    private val viewModel: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuotesListBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.loadQuotes()
        
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            viewModel.quotes.collectLatest { quotes ->
                quotes.quotes?.results?.let {
                    quotesAdapter.differ.submitList(it)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        quotesAdapter = QuotesAdapter()
        binding.rvListQuotes.apply {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}