package com.example.covid_19statistics.data.countries

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.CountryFa
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

class CountryRepositoryImpl(private val countryRemoteDataSource: CountryDataSource) :
    CountryRepository {
    override fun getCountries(): Observable<List<Country>> =
         countryRemoteDataSource.getCountries()



}