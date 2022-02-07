package com.example.covid_19statistics.feature.main

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.data.model.CovidAppEvent
import com.example.covid_19statistics.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class MainActivity : CovidAppActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setConfiguration()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

        getConstantsFromSharedPreferences()

        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemReselectedListener {
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            finish()
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "برای خروج 2 بار برگشت بزنید", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(covidAppEvent: CovidAppEvent) {
        when (covidAppEvent.type) {
            CovidAppEvent.Type.SOMETHING_WRONG -> {
                val view = showSomethingWrong(true)
                view?.findViewById<TextView>(R.id.tv_error)!!.text =
                    getString(covidAppEvent.resMessage)
                view?.findViewById<MaterialButton>(R.id.btnBack)?.setOnClickListener {
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

    private fun setConfiguration() {

        val locale = Locale("us")
        Locale.setDefault(locale)
        val configuration: Configuration = baseContext.resources.configuration
        configuration.locale = locale
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )

    }

    private fun getConstantsFromSharedPreferences() {
        DAYS_AGO = sharedPreferences.getInt(CHART_DAYS_AGO_KEY, 14)
        CHART_FONT_SIZE = sharedPreferences.getInt(FONT_SIZE_KEY, 10)

    }

}