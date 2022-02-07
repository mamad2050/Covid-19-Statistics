package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.model.Global
import com.google.gson.JsonObject
import io.reactivex.Observable

interface GlobalDataSource {

    fun getGlobal(): Observable<Global>


    fun getHistory(iso3: String,days:String):Observable<JsonObject>

}