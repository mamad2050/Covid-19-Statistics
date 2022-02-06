package com.example.covid_19statistics.feature.detailCountry

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.EXTRA_KEY_COUNTRY
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.country.CountryRepository
import com.google.gson.JsonObject

class CountryDetailViewModel(
    val bundle: Bundle,
    val repository: CountryRepository
) : CovidAppViewModel() {

    var todayLiveData = MutableLiveData<Country>()
    var yesterdayLiveData = MutableLiveData<Country>()
    var historyLiveData = MutableLiveData<JsonObject>()

    init {

        showData()
    }

    fun showData() {
        progressBarLiveData.value = true

        todayLiveData.value = bundle.getParcelable(EXTRA_KEY_COUNTRY)


        repository.getHistory(todayLiveData.value!!.countryInfo.iso3)
            .asyncNetworkRequest()
            .subscribe(object : CovidAppObserver<JsonObject>(compositeDisposable) {
                override fun onNext(t: JsonObject) {
                    historyLiveData.value = t
                }

                override fun onComplete() {

                    repository.getYesterday(todayLiveData.value!!.countryInfo.iso3)
                        .asyncNetworkRequest()
                        .subscribe(object : CovidAppObserver<Country>(compositeDisposable) {
                            override fun onNext(t: Country) {
                                yesterdayLiveData.value = t
                            }

                            override fun onComplete() {
                                progressBarLiveData.postValue(false)
                            }

                        })

                }

            })
    }

}