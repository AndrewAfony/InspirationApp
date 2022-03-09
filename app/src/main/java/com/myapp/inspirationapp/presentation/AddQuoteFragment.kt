package com.myapp.inspirationapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.databinding.FragmentAddQuoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddQuoteFragment : Fragment() {

    private var _binding: FragmentAddQuoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomNav: BottomNavigationView

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
            bottomNav.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}