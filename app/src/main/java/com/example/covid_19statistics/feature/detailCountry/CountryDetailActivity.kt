package com.example.covid_19statistics.feature.detailCountry


import android.os.Bundle
import android.widget.Toast
import com.example.covid_19statistics.common.CovidAppActivity
import com.example.covid_19statistics.common.EXTRA_KEY_COUNTRY
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.databinding.ActivityDetailCountryBinding

class CountryDetailActivity : CovidAppActivity() {

    private lateinit var binding : ActivityDetailCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras
        val country = bundle!!.getParcelable<Country>(EXTRA_KEY_COUNTRY)

        Toast.makeText(this,country!!.name,Toast.LENGTH_SHORT).show()

    }
}