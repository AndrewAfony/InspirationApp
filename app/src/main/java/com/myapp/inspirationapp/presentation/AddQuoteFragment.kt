package com.myapp.inspirationapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.databinding.FragmentAddQuoteBinding
import com.myapp.inspirationapp.domain.model.Quote
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "add_quote_fragment"

@AndroidEntryPoint
class AddQuoteFragment : Fragment() {

    private val args: AddQuoteFragmentArgs by navArgs()

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

        args.quoteId?.let { quoteId ->

            viewModel.getQuoteById(quoteId)

            viewModel.changableQuote.observe(viewLifecycleOwner) {
                binding.apply {
                    customQuote.setText(it?.content)
                    customAuthor.setText(it?.author)
                    if(it?.tags?.get(0)?.contains("famous") == true) chipGroup.check(R.id.chip_famous)
                }

                Log.d(TAG, "chip name: ${it?.tags?.get(0)}")
            }
            binding.buttonCreateQuote.text = "Save"
        }

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