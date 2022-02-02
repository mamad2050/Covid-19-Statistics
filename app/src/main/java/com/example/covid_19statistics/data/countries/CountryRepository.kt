package com.example.covid_19statistics.data.countries

import com.example.covid_19statistics.data.Country
import io.reactivex.Observable
import io.reactivex.Single

interface CountryRepository {
    fun getCountries(): Observable<List<Country>>
}