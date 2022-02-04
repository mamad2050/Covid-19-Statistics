package com.example.covid_19statistics.feature.iran

import android.app.usage.NetworkStats
import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.iran.IranRepository
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class IranViewModel(
    private val repository: IranRepository
) : CovidAppViewModel() {

    val iranLiveData = MutableLiveData<Country>()
    val yesterdayLiveData = MutableLiveData<Country>()
    val historyLiveData = MutableLiveData<JsonObject>()

    init {

        progressBarLiveData.value = true



        repository.getIran()
            .asyncNetworkRequest()
            .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable) {
                override fun onNext(t: Country) {
                    iranLiveData.value = t
                }
                override fun onComplete() {
                    repository.getHistory("ir")
                        .asyncNetworkRequest()
                        .subscribe(object : CovidAppSingleObserver<JsonObject>(compositeDisposable) {
                            override fun onNext(t: JsonObject) {
                                historyLiveData.value = t
                            }
                            override fun onComplete() {
                                repository.getYesterday()
                                    .asyncNetworkRequest()
                                    .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable) {
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
            })
    }
}