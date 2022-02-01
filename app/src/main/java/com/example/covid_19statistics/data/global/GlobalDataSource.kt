package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Single

interface GlobalDataSource {

    fun getGlobal(): Single<Global>

    fun getYesterdayStatistic(): Single<Global>

    fun getHistory(location : String):Single<JsonObject>

}