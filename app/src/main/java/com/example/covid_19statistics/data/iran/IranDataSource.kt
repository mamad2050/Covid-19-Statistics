package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.History
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONObject

interface IranDataSource {

    fun getIran(): Observable<Country>

    fun getYesterday(): Observable<Country>

    fun getHistory(location:String):Observable<JsonObject>
}