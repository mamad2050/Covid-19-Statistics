package com.example.covid_19statistics.feature.global

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.global.GlobalRepository
import com.google.gson.JsonObject

class GlobalViewModel(
    private val repository: GlobalRepository
) : CovidAppViewModel() {

    var historyLiveData = MutableLiveData<JsonObject>()
    var todayLiveData = MutableLiveData<Global>()
    var yesterdayLiveData = MutableLiveData<Global>()

    init {
        progressBarLiveData.value = true


        repository.getGlobal()
            .asyncNetworkRequest()
            .subscribe(object : CovidAppObserver<Global>(compositeDisposable) {
                override fun onNext(t: Global) {
                    todayLiveData.value = t
                }
                override fun onComplete() {

                    /* get history statistic */
                    repository.getHistory("all")
                        .asyncNetworkRequest()
                        .subscribe(object :
                            CovidAppObserver<JsonObject>(compositeDisposable) {
                            override fun onNext(t: JsonObject) {
                                historyLiveData.value = t
                            }
                            override fun onComplete() {

                                /* get yesterday statistic */
                                repository.getYesterdayStatistic()
                                    .asyncNetworkRequest()
                                    .subscribe(object :
                                        CovidAppObserver<Global>(compositeDisposable) {
                                        override fun onNext(t: Global) {
                                            yesterdayLiveData.value = t
                                        }
                                        override fun onComplete() {
                                            progressBarLiveData.postValue(false)
                                        }
                                    })
                            }
                        })
                }

            })

    }
}