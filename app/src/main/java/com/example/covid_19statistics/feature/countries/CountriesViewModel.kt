package com.example.covid_19statistics.feature.countries

import androidx.lifecycle.MutableLiveData
import com.example.covid_19statistics.common.CovidAppObserver
import com.example.covid_19statistics.common.CovidAppViewModel
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.country.CountryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CountriesViewModel(
    private val countryRepository: CountryRepository
) : CovidAppViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()

    init {

        progressBarLiveData.value = true

            countryRepository.getCountries()
                .subscribeOn(Schedulers.io())
                .doFinally { progressBarLiveData.postValue(false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CovidAppObserver<List<Country>>(compositeDisposable) {
                    override fun onNext(t: List<Country>) {
                        countriesLiveData.value = t
                    }

                    override fun onComplete() {
                    }

                })
        }
    }