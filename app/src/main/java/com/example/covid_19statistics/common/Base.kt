package com.example.covid_19statistics.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid_19statistics.R
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

abstract class CovidAppFragment() : Fragment(), CovidAppView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}

abstract class CovidAppActivity() : AppCompatActivity(), CovidAppView {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of CoordinatorLayout")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this


}

interface CovidAppView {

    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setProgressIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading, it, false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE

            }

        }
    }

    fun showSomethingWrong(mustShow: Boolean): View? {

        rootView?.let {
            viewContext?.let { context ->
                var somethingWrong = it.findViewById<View>(R.id.somethingWrongRootView)
                if (somethingWrong == null) {
                    somethingWrong = LayoutInflater.from(context)
                        .inflate(R.layout.view_something_wrong, it, false)
                    it.addView(somethingWrong)
                }
                somethingWrong.visibility = if (mustShow) View.VISIBLE else View.GONE
                return somethingWrong
            }
        }
        return null
    }


    fun showConnectionLost(mustShow: Boolean): View? {

        rootView?.let {
            viewContext?.let { context ->
                var connectionLost = it.findViewById<View>(R.id.connectionLostRootView)
                if (connectionLost == null) {
                    connectionLost = LayoutInflater.from(context)
                        .inflate(R.layout.view_connection_lost, it, false)
                    it.addView(connectionLost)
                }
                connectionLost.visibility = if (mustShow) View.VISIBLE else View.GONE
                return connectionLost
            }
        }
        return null
    }

}



abstract class CovidAppViewModel:ViewModel(){
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}