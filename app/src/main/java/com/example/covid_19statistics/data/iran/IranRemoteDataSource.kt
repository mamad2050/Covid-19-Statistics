package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class IranRemoteDataSource(private val apiService: ApiService) : IranDataSource {

    override fun getIran(): Single<Country> = apiService.getIran()

    override fun getYesterday(): Single<Country> = apiService.getYesterday()

    override fun getHistory(location: String): Single<JsonObject> =
        apiService.getHistory(location)


}