package com.example.covid_19statistics.data.countries

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

class CountryRemoteDataSource(private val apiService: ApiService):CountryDataSource {

    override fun getCountries(): Observable<List<Country>> = apiService.getCountries()


}