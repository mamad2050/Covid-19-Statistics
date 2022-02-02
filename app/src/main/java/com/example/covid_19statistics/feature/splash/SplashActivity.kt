package com.example.covid_19statistics.feature.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.covid_19statistics.common.CovidAppActivity
import com.example.covid_19statistics.databinding.ActivitySplashBinding
import com.example.covid_19statistics.feature.main.MainActivity


class SplashActivity : CovidAppActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideStatusBar()

        checkNetworkCondition()

        binding.swipeRefreshLayout.setOnRefreshListener(this)

    }

    private fun checkNetworkConnection(): Boolean {

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(network) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else
            return false
    }

    private fun goToHomeActivity() {
        binding.pbSplash.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun checkNetworkCondition() {
        if (checkNetworkConnection()) {
            binding.swipeRefreshLayout.isEnabled = false
            goToHomeActivity()
        } else {
            noNetwork()
        }
    }

    private fun noNetwork() {

        Toast.makeText(this, "اینترنت وصل نیست :)", Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        Toast.makeText(this, "در حال انتقال به حالت آنلاین", Toast.LENGTH_SHORT).show()
        checkNetworkCondition()
    }

    private fun hideStatusBar(){
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        supportActionBar?.hide()
    }
}