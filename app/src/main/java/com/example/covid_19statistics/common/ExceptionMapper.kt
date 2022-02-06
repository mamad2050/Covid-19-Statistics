package com.example.covid_19statistics.common

import com.example.covid_19statistics.R
import com.example.covid_19statistics.data.CovidAppEvent
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException

import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class ExceptionMapper {

    companion object{
        fun map(throwable: Throwable) : CovidAppEvent{
               if (throwable is HttpException){
                   try {
                       val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                       val errorMessage = errorJsonObject.getString("message")
                       return CovidAppEvent(
                           CovidAppEvent.Type.SOMETHING_WRONG,
                           stringMessage = errorMessage
                       )
                   } catch (exception: Exception) {
                       Timber.e(exception)
                   }
               } else if (throwable is UnknownHostException || throwable is ConnectException) {
                   return CovidAppEvent(CovidAppEvent.Type.CONNECTION_LOST, stringMessage = "")
               }

            return CovidAppEvent(
                CovidAppEvent.Type.SOMETHING_WRONG,
                resMessage = R.string.unknown_error
            )

        }
    }

}