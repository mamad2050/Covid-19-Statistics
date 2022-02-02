package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

class GlobalRepositoryImpl(private val globalRemoteDataSource: GlobalDataSource) :
    GlobalRepository {

    override fun getGlobal(): Observable<Global> = globalRemoteDataSource.getGlobal()

    override fun getYesterdayStatistic(): Observable<Global> = globalRemoteDataSource.getYesterdayStatistic()

    override fun getHistory(location : String): Observable<JsonObject> = globalRemoteDataSource.getHistory(location)

}