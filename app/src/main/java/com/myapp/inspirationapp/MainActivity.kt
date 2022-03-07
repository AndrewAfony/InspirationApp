package com.myapp.inspirationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myapp.inspirationapp.databinding.ActivityMainBinding
import com.myapp.inspirationapp.presentation.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: QuotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.quoteNavHost) as NavHostFragment
        val navController = navHost.navController

        binding.bottomNavigation.setupWithNavController(navController)

        viewModel.run {
            getRandomQuote()
            loadQuotes()
        }
    }
}