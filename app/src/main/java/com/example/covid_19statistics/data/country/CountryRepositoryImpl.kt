package com.example.covid_19statistics.data.country

import com.example.covid_19statistics.data.Country
import com.google.gson.JsonObject
import io.reactivex.Observable

class CountryRepositoryImpl(private val remoteDataSource: CountryDataSource) :
    CountryRepository {
    override fun getCountries(): Observable<List<Country>> =
         remoteDataSource.getAllCountries()

    override fun getToday(iso3:String): Observable<Country> = remoteDataSource.getToday(iso3)

    override fun getYesterday(iso3:String): Observable<Country> =
        remoteDataSource.getYesterday(iso3)

    override fun getHistory(iso3: String): Observable<JsonObject> =
        remoteDataSource.getHistory(iso3)

}