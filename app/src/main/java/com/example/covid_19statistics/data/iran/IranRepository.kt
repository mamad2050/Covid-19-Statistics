package com.example.covid_19statistics.data.iran

import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.History
import io.reactivex.Single

interface IranRepository {
    fun getIran(): Single<Country>

    fun getIranYesterday(): Single<Country>

    fun getIranHistory(): Single<List<History>>
}