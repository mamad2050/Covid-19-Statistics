package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.History
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONObject

class IranRepositoryImpl(private val iranRemoteDataSource: IranDataSource) :
    IranRepository {

    override fun getIran(): Single<Country> = iranRemoteDataSource.getIran()

    override fun getIranYesterday(): Single<Country> = iranRemoteDataSource.getIranYesterday()

    override fun getIranHistory(): Single<JsonObject> = iranRemoteDataSource.getIranHistory()


}