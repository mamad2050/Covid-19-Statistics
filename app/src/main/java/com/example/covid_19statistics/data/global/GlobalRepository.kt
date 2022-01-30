package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import io.reactivex.Single

interface GlobalRepository {
    fun getGlobal(): Single<Global>
}