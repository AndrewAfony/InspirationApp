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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.adapters.QuotesAdapter
import com.myapp.inspirationapp.databinding.FragmentQuotesListBinding
import com.myapp.inspirationapp.presentation.FavoriteQuotesFragmentDirections
import com.myapp.inspirationapp.presentation.QuotesViewModel
import com.myapp.inspirationapp.utils.shareQuote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuotesListFragment : Fragment() {

    private var _binding: FragmentQuotesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var quotesAdapter: QuotesAdapter

    private lateinit var bottomNavigation: BottomNavigationView

    private val viewModel: QuotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuotesListBinding.inflate(inflater, container, false)

        bottomNavigation = activity?.findViewById(R.id.bottom_navigation)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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
        quotesAdapter = QuotesAdapter(
            onFavoriteClick = { quote ->
                viewModel.saveQuote(quote)
            },
            onShareClick = { text ->
                shareQuote(requireContext(), text)
            },
            onQuoteClick = {
                val action = QuotesListFragmentDirections.actionChangeQuoteFromList(it)
                view?.findNavController()?.navigate(action)
            }
        )
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

    private fun showPopupMenu(context: Context, view: View, position: Int) {

        PopupMenu(context, view).apply {

            setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when (it.itemId) {
                    R.id.save_to_favorite -> {
                        val quote = quotesAdapter.differ.currentList[position]
                        viewModel.saveQuote(quote)
                        Snackbar.make(binding.root, "Saved", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                viewModel.deleteQuote(quote)
                            }
                            .show()
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
}