package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.google.gson.JsonObject
import io.reactivex.Observable

interface IranRepository {
    fun getIran(): Observable<Country>

    fun getYesterday(): Observable<Country>

    fun getHistory(location : String): Observable<JsonObject>
}