package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.History
import com.example.covid_19statistics.services.http.ApiService
import io.reactivex.Single

class IranRemoteDataSource(private val apiService: ApiService):IranDataSource {

    override fun getIran(): Single<Country> =apiService.getIran()

    override fun getIranYesterday(): Single<Country> =apiService.getIranYesterday()

    override fun getIranHistory(): Single<List<History>> = apiService.getIranHistory()


}