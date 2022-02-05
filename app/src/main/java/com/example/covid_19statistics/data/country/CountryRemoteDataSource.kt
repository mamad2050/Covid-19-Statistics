package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable

class CountryRemoteDataSource(private val apiService: ApiService):CountryDataSource {

    override fun getAllCountries(): Observable<List<Country>> = apiService.getAllCountries()

    override fun getToday(iso3:String): Observable<Country> = apiService.getTodayCountry(iso3)

    override fun getYesterday(iso3:String): Observable<Country> = apiService.getYesterdayCountry(iso3)

    override fun getHistory(iso3: String): Observable<JsonObject> =
        apiService.getHistory(iso3)

}