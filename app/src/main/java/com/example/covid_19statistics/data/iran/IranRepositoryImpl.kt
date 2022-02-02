package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single

class IranRepositoryImpl(private val iranRemoteDataSource: IranDataSource) :
    IranRepository {

    override fun getIran(): Observable<Country> = iranRemoteDataSource.getIran()

    override fun getYesterday(): Observable<Country> =
        iranRemoteDataSource.getYesterday()

    override fun getHistory(location: String): Observable<JsonObject> =
        iranRemoteDataSource.getHistory(location)


}