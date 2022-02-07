package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.model.Country
import com.google.gson.JsonObject
import io.reactivex.Observable

class CountryRepositoryImpl(private val remoteDataSource: CountryDataSource) :
    CountryRepository {
    override fun getCountries(): Observable<List<Country>> =
         remoteDataSource.getAllCountries()

    override fun getTodayStatistics(iso3:String): Observable<Country> = remoteDataSource.getTodayStatistics(iso3)

    override fun getHistory(iso3: String,days:String): Observable<JsonObject> =
        remoteDataSource.getHistory(iso3,days)

}