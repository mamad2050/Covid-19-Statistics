package com.example.covid_19statistics.feature.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.CovidAppActivity
import com.example.covid_19statistics.databinding.ActivityMainBinding

class MainActivity : CovidAppActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnNavigationItemReselectedListener {

        }

    }

}