package com.example.covid_19statistics.data.model

data class Global(
    val active: Int,
    val cases: Int,
    val critical: Int,
    val deaths: Int,
    val recovered: Int,
    val tests: Long,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long,
    val population: Long
)