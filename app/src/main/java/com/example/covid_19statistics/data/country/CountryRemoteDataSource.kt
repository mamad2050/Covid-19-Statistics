package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.model.Country
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable

class CountryRemoteDataSource(private val apiService: ApiService):CountryDataSource {

    override fun getAllCountries(): Observable<List<Country>> = apiService.getAllCountries()

    override fun getTodayStatistics(iso3:String): Observable<Country> = apiService.getCountry(iso3)

    override fun getHistory(iso3: String,days:String ): Observable<JsonObject> =
        apiService.getHistory(iso3 ,days)

}