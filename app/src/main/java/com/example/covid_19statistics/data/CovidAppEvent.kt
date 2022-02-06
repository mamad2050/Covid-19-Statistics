package com.example.covid_19statistics.data

import androidx.annotation.StringRes

class CovidAppEvent(
    val type: Type,
    @StringRes val resMessage: Int = 0,
    val stringMessage: String? = null
) {
    enum class Type {
        SOMETHING_WRONG , CONNECTION_LOST
    }
}