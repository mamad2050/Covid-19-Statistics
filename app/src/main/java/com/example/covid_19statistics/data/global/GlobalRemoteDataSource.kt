package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

class GlobalRemoteDataSource(private val apiService: ApiService) : GlobalDataSource {

    override fun getGlobal(): Observable<Global> = apiService.getGlobal()

    override fun getYesterdayStatistic(): Observable<Global> = apiService.getGlobalYesterday()

    override fun getHistory(location: String): Observable<JsonObject> = apiService.getHistory(location)


}