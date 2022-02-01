package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class GlobalRemoteDataSource(private val apiService: ApiService):GlobalDataSource {

    override fun getGlobal(): Single<Global> = apiService.getGlobal()

    override fun getGlobalYesterday(): Single<Global> = apiService.getGlobalYesterday()

    override fun getGlobalHistory(): Single<JsonObject> = apiService.getGlobalHistory()


}