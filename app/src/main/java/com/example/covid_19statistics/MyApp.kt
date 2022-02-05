package com.example.covid_19statistics

import android.app.Application
import android.os.Bundle
import com.example.covid_19statistics.data.country.CountryRemoteDataSource
import com.example.covid_19statistics.data.country.CountryRepository
import com.example.covid_19statistics.data.country.CountryRepositoryImpl
import com.example.covid_19statistics.data.global.GlobalRemoteDataSource
import com.example.covid_19statistics.data.global.GlobalRepository
import com.example.covid_19statistics.data.global.GlobalRepositoryImpl
import com.example.covid_19statistics.feature.countries.CountriesViewModel
import com.example.covid_19statistics.feature.detailCountry.CountryDetailViewModel
import com.example.covid_19statistics.feature.global.GlobalViewModel
import com.example.covid_19statistics.feature.iran.IranViewModel
import com.example.covid_19statistics.services.http.createApiServiceInstance
import com.example.covid_19statistics.services.imageloader.FrescoImageLoadingService
import com.example.covid_19statistics.services.imageloader.ImageLoadingService
import com.facebook.drawee.backends.pipeline.Fresco
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { }

        Fresco.initialize(this)

        Timber.plant(Timber.DebugTree())

        val myModules = module {

            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }

            factory<CountryRepository> { CountryRepositoryImpl(CountryRemoteDataSource(get())) }
            factory<GlobalRepository> { GlobalRepositoryImpl(GlobalRemoteDataSource(get())) }


            viewModel { CountriesViewModel(get()) }
            viewModel { GlobalViewModel(get()) }
            viewModel { IranViewModel(get()) }
            viewModel { (bundle: Bundle )-> CountryDetailViewModel(bundle,get()) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(myModules)
        }

    }

}
