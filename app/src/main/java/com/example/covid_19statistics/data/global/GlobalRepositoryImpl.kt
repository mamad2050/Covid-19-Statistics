package com.example.covid_19statistics.data.global

import com.example.covid_19statistics.data.Global
import io.reactivex.Single

class GlobalRepositoryImpl(private val globalRemoteDataSource: GlobalDataSource) :
    GlobalRepository {

    override fun getGlobal(): Single<Global> = globalRemoteDataSource.getGlobal()

    override fun getGlobalYesterday(): Single<Global> = globalRemoteDataSource.getGlobalYesterday()

}