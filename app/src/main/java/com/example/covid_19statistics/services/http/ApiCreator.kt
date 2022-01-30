package com.example.covid_19statistics.services.http

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object ApiCreator {
    var requestQueue: RequestQueue? = null
    fun getRequestQueue(context: Context?): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }
        return requestQueue
    }
}