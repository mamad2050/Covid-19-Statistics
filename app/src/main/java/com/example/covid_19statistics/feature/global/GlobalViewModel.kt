package com.example.covid_19statistics.feature.global

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.global.GlobalRepository
import com.google.gson.JsonObject
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class GlobalViewModel(
    private val repository: GlobalRepository
) : CovidAppViewModel() {

    var historyLiveData = MutableLiveData<JsonObject>()
    var globalLiveData = MutableLiveData<Global>()
    var yesterdayLiveData = MutableLiveData<Global>()

    init {
        progressBarLiveData.value = true


        repository.getGlobal()
            .asyncNetworkRequest()
            .subscribe(object : CovidAppSingleObserver<Global>(compositeDisposable) {
                override fun onNext(t: Global) {
                    globalLiveData.value = t
                }
                override fun onComplete() {

                    /* get history statistic */
                    repository.getHistory("all")
                        .asyncNetworkRequest()
                        .subscribe(object :
                            CovidAppSingleObserver<JsonObject>(compositeDisposable) {
                            override fun onNext(t: JsonObject) {
                                historyLiveData.value = t
                            }
                            override fun onComplete() {

                                /* get yesterday statistic */
                                repository.getYesterdayStatistic()
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