package com.example.covid_19statistics.data.countries

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.services.http.ApiService
import io.reactivex.Single

class CountryRemoteDataSource(private val apiService: ApiService):CountryDataSource {

    override fun getCountries(): Single<List<Country>> = apiService.getCountries()


}