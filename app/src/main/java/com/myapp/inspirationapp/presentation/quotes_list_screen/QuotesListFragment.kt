package com.myapp.inspirationapp.presentation.quotes_list_screen

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.inspirationapp.R
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

    private lateinit var bottomNavigation: BottomNavigationView

    private val viewModel: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuotesListBinding.inflate(inflater, container, false)

        bottomNavigation = activity?.findViewById(R.id.bottom_navigation)!!

        viewModel.loadQuotes()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view.context, view)
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

    private fun setupRecyclerView(context: Context, view: View) {
        quotesAdapter = QuotesAdapter {
            showMenu(it.context, it)
        }
        binding.rvListQuotes.apply {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@QuotesListFragment.scrollListener)
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

    private fun showMenu(context: Context, view: View) {

        PopupMenu(context, view).apply {

            setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when (it.itemId) {
                    R.id.save_to_favorite -> {
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.share -> {
                        true
                    }
                    else -> false
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setForceShowIcon(true)
            }

            inflate(R.menu.quote_menu)
            show()

        }

    }

    private fun showToast(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}