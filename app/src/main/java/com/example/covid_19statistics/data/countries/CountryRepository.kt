package com.example.covid_19statistics.data.countries

import com.example.covid_19statistics.data.Country
import io.reactivex.Single

interface CountryRepository {
    fun getCountries(): Single<List<Country>>
}