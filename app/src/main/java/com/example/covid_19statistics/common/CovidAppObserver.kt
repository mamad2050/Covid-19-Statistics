package com.example.covid_19statistics.common

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class CovidAppObserver<T>(private val compositeDisposable: CompositeDisposable) :
    Observer<T> {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        EventBus.getDefault().post(ExceptionMapper.map(e))
        Timber.e(e)
    }


}