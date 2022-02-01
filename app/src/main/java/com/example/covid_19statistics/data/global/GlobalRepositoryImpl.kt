package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import com.google.gson.JsonObject
import io.reactivex.Single

class GlobalRepositoryImpl(private val globalRemoteDataSource: GlobalDataSource) :
    GlobalRepository {

    override fun getGlobal(): Single<Global> = globalRemoteDataSource.getGlobal()

    override fun getYesterdayStatistic(): Single<Global> = globalRemoteDataSource.getYesterdayStatistic()

    override fun getHistory(location : String): Single<JsonObject> = globalRemoteDataSource.getHistory(location)

}