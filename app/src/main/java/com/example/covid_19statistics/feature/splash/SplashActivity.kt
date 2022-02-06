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
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.CovidAppActivity
import com.example.covid_19statistics.common.ExceptionMapper
import com.example.covid_19statistics.data.CovidAppEvent
import com.example.covid_19statistics.databinding.ActivitySplashBinding
import com.example.covid_19statistics.feature.main.MainActivity
import com.google.android.material.button.MaterialButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.net.UnknownHostException


class SplashActivity : CovidAppActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideStatusBar()

        checkNetworkCondition()

    }

    private fun checkNetworkConnection(): Boolean {
        showConnectionLost(false)
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
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun checkNetworkCondition() {
        if (checkNetworkConnection()) {
            goToHomeActivity()
        } else {
            val connectionView = showConnectionLost(true)
            connectionView?.findViewById<MaterialButton>(R.id.btn_retry)?.setOnClickListener {
                checkNetworkCondition()
            }
        }
    }


    private fun hideStatusBar() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        supportActionBar?.hide()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(covidAppEvent: CovidAppEvent) {
        when (covidAppEvent.type) {
            CovidAppEvent.Type.SOMETHING_WRONG -> {
                val connectionView = showSomethingWrong(true)
                connectionView?.findViewById<MaterialButton>(R.id.btnBack)?.setOnClickListener {
                    showSomethingWrong(false)
                    finish()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}