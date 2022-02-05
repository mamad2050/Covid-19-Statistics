package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.Country
import com.google.gson.JsonObject
import io.reactivex.Observable

interface CountryRepository {

    fun getCountries(): Observable<List<Country>>

    fun getToday(iso3:String): Observable<Country>

    fun getYesterday(iso3:String): Observable<Country>

    fun getHistory(iso3 : String): Observable<JsonObject>

}