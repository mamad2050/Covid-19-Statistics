package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Observable

interface GlobalDataSource {

    fun getGlobal(): Observable<Global>

    fun getYesterdayStatistic(): Observable<Global>

    fun getHistory(location : String):Observable<JsonObject>

}