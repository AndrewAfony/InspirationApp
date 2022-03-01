package com.myapp.inspirationapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.adapters.QuotesAdapter
import com.myapp.inspirationapp.databinding.FragmentQuotesListBinding

class QuotesListFragment : Fragment() {

    private var _binding: FragmentQuotesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var quotesAdapter: QuotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupRecyclerView()

        _binding = FragmentQuotesListBinding.inflate(inflater, container, false)



        return binding.root
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