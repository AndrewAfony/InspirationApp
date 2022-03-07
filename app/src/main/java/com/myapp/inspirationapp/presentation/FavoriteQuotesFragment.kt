package com.myapp.inspirationapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.adapters.QuotesAdapter
import com.myapp.inspirationapp.databinding.FragmentFavoriteQuotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteQuotesFragment : Fragment() {

    private var _binding: FragmentFavoriteQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var quotesAdapter: QuotesAdapter

    private lateinit var bottomNavigation: BottomNavigationView

    private val viewModel: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteQuotesBinding.inflate(inflater, container, false)

        bottomNavigation = activity?.findViewById(R.id.bottom_navigation)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.getSavedQuotes().collect { quotes ->
                quotesAdapter.differ.submitList(quotes)
            }
        }
    }

    private fun setupRecyclerView() {
        quotesAdapter = QuotesAdapter { _, _ -> }
        binding.rvFavorite.apply {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(this@FavoriteQuotesFragment.scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && bottomNavigation.isShown) {
                bottomNavigation.visibility = View.GONE
            } else if (dy < 0) {
                bottomNavigation.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}