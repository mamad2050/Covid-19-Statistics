package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.model.Global
import com.google.gson.JsonObject
import io.reactivex.Observable

class GlobalRepositoryImpl(private val globalRemoteDataSource: GlobalDataSource) :
    GlobalRepository {

    override fun getGlobal(): Observable<Global> = globalRemoteDataSource.getGlobal()

    override fun getHistory(iso3: String,days:String): Observable<JsonObject> =
        globalRemoteDataSource.getHistory(iso3,days)

}