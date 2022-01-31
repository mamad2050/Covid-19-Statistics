package com.example.covid_19statistics.feature.global

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppSingleObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.common.asyncNetworkRequest
import com.example.covid_19statistics.data.Global
import com.example.covid_19statistics.data.global.GlobalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GlobalViewModel(
    private val globalRepository: GlobalRepository
) : CovidAppViewModel() {
     var globalLiveData = MutableLiveData<Global>()
     var globalYesterdayLiveData = MutableLiveData<Global>()

    init {
        progressBarLiveData.value = true

        globalRepository.getGlobal()
            .doOnSuccess {
                globalRepository.getGlobalYesterday()
                    .asyncNetworkRequest()
                    .subscribe(object : CovidAppSingleObserver<Global>(compositeDisposable){
                        override fun onSuccess(t: Global) {
                            globalYesterdayLiveData.value = t
                        }

                    })
            }
            .doAfterSuccess { progressBarLiveData.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CovidAppSingleObserver<Global>(compositeDisposable) {
                override fun onSuccess(t: Global) {
                    globalLiveData.value = t
                }
            })
    }
}