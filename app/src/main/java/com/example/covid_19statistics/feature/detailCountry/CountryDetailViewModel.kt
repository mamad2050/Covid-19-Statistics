package com.example.covid_19statistics.feature.detailCountry

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.data.model.Country
import com.example.covid_19statistics.data.country.CountryRepository
import com.google.gson.JsonObject

class CountryDetailViewModel(
    private val bundle: Bundle,
    private val repository: CountryRepository
) : CovidAppViewModel() {

    var todayLiveData = MutableLiveData<Country>()
    var historyLiveData = MutableLiveData<JsonObject>()

    init {

        showData()
    }

    fun showData() {
        progressBarLiveData.value = true

        todayLiveData.value = bundle.getParcelable(EXTRA_KEY_COUNTRY)


        repository.getHistory(todayLiveData.value!!.countryInfo.iso3, (DAYS_AGO+1).toString())
            .asyncNetworkRequest()
            .subscribe(object : CovidAppObserver<JsonObject>(compositeDisposable) {
                override fun onNext(t: JsonObject) {
                    historyLiveData.value = t
                }

                override fun onComplete() {
                    progressBarLiveData.postValue(false)
                }
            })
    }

}