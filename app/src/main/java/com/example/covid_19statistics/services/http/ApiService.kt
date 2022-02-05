package com.example.covid_19statistics.services.http

import com.example.covid_19statistics.common.daysAgo
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("countries?allowNull=1")
    fun getAllCountries(): Observable<List<Country>>

    @GET("countries/{iso3}?allowNull=1")
    fun getTodayCountry(@Path("iso3") location: String): Observable<Country>

    @GET("countries/{iso3}?yesterday=1&allowNull=1")
    fun getYesterdayCountry(@Path("iso3") location: String): Observable<Country>

    @GET("all")
    fun getGlobal(): Observable<Global>


    @GET("all?yesterday=1&allowNull=1")
    fun getGlobalYesterday(): Observable<Global>

    @GET("historical/{iso3}?lastdays=${daysAgo + 1}")
    fun getHistory(@Path("iso3") iso3: String): Observable<JsonObject>

}

fun createApiServiceInstance(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    val retrofit = Retrofit.Builder().baseUrl("https://disease.sh/v3/covid-19/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)

}