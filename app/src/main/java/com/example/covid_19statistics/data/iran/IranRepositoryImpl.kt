package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.google.gson.JsonObject
import io.reactivex.Single

class IranRepositoryImpl(private val iranRemoteDataSource: IranDataSource) :
    IranRepository {

    override fun getIran(): Single<Country> = iranRemoteDataSource.getIran()

    override fun getYesterday(): Single<Country> =
        iranRemoteDataSource.getYesterday()

    override fun getHistory(location: String): Single<JsonObject> =
        iranRemoteDataSource.getHistory(location)


}