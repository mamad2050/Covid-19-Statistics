package com.example.covid_19statistics.feature.iran

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.iran.IranRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class IranViewModel(
    private val iranRepository: IranRepository
) : CovidAppViewModel() {

    val iranLiveData = MutableLiveData<Country>()

    val yesterdayLiveData = MutableLiveData<Country>()

    val historyLiveData = MutableLiveData<JsonObject>()

    init {

        progressBarLiveData.value = true


        iranRepository.getHistory("ir")
            .asyncNetworkRequest()
            .subscribe(object : CovidAppSingleObserver<JsonObject>(compositeDisposable){
                override fun onSuccess(t: JsonObject) {
                    historyLiveData.value = t
                }
            })


        iranRepository.getIran()
            .doOnSuccess {
                iranRepository.getYesterday()
                    .asyncNetworkRequest()
                    .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable) {
                        override fun onSuccess(t: Country) {
                            yesterdayLiveData.value = t
                        }
                    })
            }
            .subscribeOn(Schedulers.io())
            .doAfterSuccess{ progressBarLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable) {
                override fun onSuccess(t: Country) {
                    iranLiveData.value = t
                }
            })


    }
}