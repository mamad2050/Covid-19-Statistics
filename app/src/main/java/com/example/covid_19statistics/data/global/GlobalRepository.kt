package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

interface GlobalRepository {

    fun getGlobal(): Observable<Global>

    fun getYesterdayStatistic(): Observable<Global>

    fun getHistory(location : String): Observable<JsonObject>
}