package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import io.reactivex.Single

interface GlobalDataSource {
    fun getGlobal(): Single<Global>
}