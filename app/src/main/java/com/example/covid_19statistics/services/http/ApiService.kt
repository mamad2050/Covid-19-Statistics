package com.example.covid_19statistics.services.http

import com.example.covid_19statistics.common.daysAgo
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.History
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("countries?allowNull=1")
    fun getCountries(): Single<List<Country>>

    @GET("countries/ir?allowNull=1")
    fun getIran():Single<Country>

    @GET("countries/iran?yesterday=1&allowNull=1")
    fun getIranYesterday():Single<Country>

    @GET("all")
    fun getGlobal():Single<Global>

    @GET("historical/iran?lastdays=$daysAgo")
    fun getIranHistory():Single<List<History>>



}

fun createApiServiceInstance(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    val retrofit = Retrofit.Builder().baseUrl("https://disease.sh/v3/covid-19/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)

}