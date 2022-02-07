package com.example.covid_19statistics.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val cases: Int,
    @SerializedName("country")
    var name: String,
    val critical: Int,
    val deaths: Int,
    val population: Int,
    val recovered: Int,
    val tests: Int,
    val todayCases: Int?,
    val todayDeaths: Int?,
    val todayRecovered: Int?,
    val updated: Long,
    val countryInfo: CountryInfo
):Parcelable


