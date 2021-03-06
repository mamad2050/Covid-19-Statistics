package com.example.covid_19statistics.feature.iran

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.common.DAYS_AGO
import com.example.covid_19statistics.data.model.Country
import com.example.covid_19statistics.data.country.CountryRepository
import com.google.gson.JsonObject


class IranViewModel(
    private val repository: CountryRepository
) : CovidAppViewModel() {

    val todayLiveData = MutableLiveData<Country>()
    val historyLiveData = MutableLiveData<JsonObject>()

    init {

        showData()

    }

    fun showData(){
        progressBarLiveData.value = true

        repository.getTodayStatistics("irn")
            .asyncNetworkRequest()
            .subscribe(object : CovidAppObserver<Country>(compositeDisposable) {
                override fun onNext(t: Country) {
                    todayLiveData.value = t
                }
                override fun onComplete() {
                    repository.getHistory("irn", (DAYS_AGO+1).toString())
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
            })
    }
}