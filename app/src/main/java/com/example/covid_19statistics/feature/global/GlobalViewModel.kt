package com.example.covid_19statistics.feature.global

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.global.GlobalRepository
import com.google.gson.JsonObject

class GlobalViewModel(
    private val globalRepository: GlobalRepository
) : CovidAppViewModel() {

    var historyLiveData = MutableLiveData<JsonObject>()
    var globalLiveData = MutableLiveData<Global>()
    var yesterdayLiveData = MutableLiveData<Global>()

    init {
        progressBarLiveData.value = true

        globalRepository.getGlobal()
            .asyncNetworkRequest()
            .subscribe(object : CovidAppSingleObserver<Global>(compositeDisposable) {
                override fun onNext(t: Global) {
                    globalLiveData.value = t
                }
                override fun onComplete() {

                    /* get history statistic */
                    globalRepository.getHistory("all")
                        .asyncNetworkRequest()
                        .subscribe(object :
                            CovidAppSingleObserver<JsonObject>(compositeDisposable) {
                            override fun onNext(t: JsonObject) {
                                historyLiveData.value = t
                            }
                            override fun onComplete() {

                                /* get yesterday statistic */
                                globalRepository.getYesterdayStatistic()
                                    .asyncNetworkRequest()
                                    .subscribe(object :
                                        CovidAppSingleObserver<Global>(compositeDisposable) {
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