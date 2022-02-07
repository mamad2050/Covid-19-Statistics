package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.model.Country
import com.google.gson.JsonObject
import io.reactivex.Observable

interface CountryRepository {

    fun getCountries(): Observable<List<Country>>

    fun getTodayStatistics(iso3:String): Observable<Country>

    fun getHistory(iso3: String,days:String): Observable<JsonObject>

}