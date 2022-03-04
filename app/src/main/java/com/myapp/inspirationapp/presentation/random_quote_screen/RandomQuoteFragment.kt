package com.myapp.inspirationapp.presentation.random_quote_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.databinding.FragmentRandomQuoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomQuoteFragment : Fragment() {

    private val viewModel: RandomQuoteViewModel by activityViewModels()

    private var _binding: FragmentRandomQuoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomQuoteBinding.inflate(inflater, container, false)

        viewModel.randomQuote.observe(viewLifecycleOwner) {
            binding.quote.text = it.content
            binding.author.text = it.author
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}