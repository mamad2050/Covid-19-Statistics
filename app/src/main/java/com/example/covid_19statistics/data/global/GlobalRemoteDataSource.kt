package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.model.Global
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable

class GlobalRemoteDataSource(private val apiService: ApiService) : GlobalDataSource {

    override fun getGlobal(): Observable<Global> = apiService.getGlobal()


    override fun getHistory(iso3: String, days: String): Observable<JsonObject> =
        apiService.getHistory(iso3, days)


}