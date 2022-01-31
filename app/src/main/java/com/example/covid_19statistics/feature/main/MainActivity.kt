package com.example.covid_19statistics.feature.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.CovidAppActivity
import com.example.covid_19statistics.databinding.ActivityMainBinding


class MainActivity : CovidAppActivity() {

    private var doubleBackToExitPressedOnce = false
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

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce){
        super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this,"برای خروج 2 بار برگشت بزنید",Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false },2000)

    }

}