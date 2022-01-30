package com.example.covid_19statistics.feature.iran

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.iran.IranRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IranViewModel(
    private val iranRepository: IranRepository
) : CovidAppViewModel() {

     val iranLiveData = MutableLiveData<Country>()

     val iranYesterdayLiveData = MutableLiveData<Country>()



    init {
        progressBarLiveData.value = true

        iranRepository.getIran()
            .doOnSuccess {
                iranRepository.getIranYesterday()
                    .asyncNetworkRequest()
                    .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable){
                        override fun onSuccess(t: Country) {
                            iranYesterdayLiveData.value = t
                        }
                    })
            }
            .doAfterSuccess() { progressBarLiveData.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CovidAppSingleObserver<Country>(compositeDisposable) {
                override fun onSuccess(t: Country) {
                    iranLiveData.value = t
                }
            })



    }
}