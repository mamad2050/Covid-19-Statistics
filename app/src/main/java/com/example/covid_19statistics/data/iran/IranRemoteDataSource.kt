package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Observable

class IranRemoteDataSource(private val apiService: ApiService) : IranDataSource {

    override fun getIran(): Observable<Country> = apiService.getIran()

    override fun getYesterday(): Observable<Country> = apiService.getIranYesterday()

    override fun getHistory(location: String): Observable<JsonObject> =
        apiService.getHistory(location)


}