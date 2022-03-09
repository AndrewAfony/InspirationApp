package com.myapp.inspirationapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.databinding.FragmentAddQuoteBinding
import com.myapp.inspirationapp.domain.model.Quote
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddQuoteFragment : Fragment() {

    private var _binding: FragmentAddQuoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomNav: BottomNavigationView

    private val viewModel: QuotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddQuoteBinding.inflate(inflater, container, false)

        bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav.visibility = View.GONE

        binding.toolbar.setNavigationOnClickListener {
            view.findNavController().navigateUp()
        }

        binding.buttonCreateQuote.setOnClickListener {

            var quoteText: String = ""
            var author: String = ""
            val tag: MutableList<String> = mutableListOf()

            if(binding.customQuote.text.toString().isBlank()) {
                binding.customQuote.error = "Can't be empty!"
            } else {
                quoteText = binding.customQuote.text.toString()
            }

            if(binding.customAuthor.text.toString().isBlank()){
                binding.customAuthor.error = "Can't be empty!"
            } else {
                author = binding.customAuthor.text.toString()
            }

            val selected = view.findViewById<Chip>(binding.chipGroup.checkedChipId)

            if(selected == null) {
                Snackbar.make(view, "Select tag", Snackbar.LENGTH_SHORT).show()
            } else {
                tag.add(selected.text.toString())
            }

            val quote = Quote(
                _id = UUID.randomUUID().toString(),
                author = author,
                content = quoteText,
                tags = tag
            )

            viewModel.saveQuote(quote)

            binding.apply {
                customQuote.text.clear()
                customAuthor.text.clear()
                chipGroup.clearCheck()
            }

            Snackbar.make(view, "Quote added", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}