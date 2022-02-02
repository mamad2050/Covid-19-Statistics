package com.example.covid_19statistics.common

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ClickableViewAccessibility")
fun View.implementSpringAnimationTrait() {
    val scaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 0.90f)
    val scaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 0.90f)

    setOnTouchListener { v, event ->
        Timber.i(event.action.toString())
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleXAnim.start()

                scaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleYAnim.start()

            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                scaleXAnim.cancel()
                scaleYAnim.cancel()
                val reverseScaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
                reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleXAnim.start()

                val reverseScaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
                reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleYAnim.start()

            }
        }

        false
    }

}

fun decimalFormatter(num: String): String {
    val decimalFormat = DecimalFormat("###,###")
    return decimalFormat.format(num.toInt())
}

fun valueAnimator(num: String, tv: TextView) {
    val valueAnimator = ValueAnimator.ofInt(0, num.toInt())
    valueAnimator.duration = 1000
    valueAnimator.addUpdateListener {
        tv.text = decimalFormatter(it.animatedValue.toString())
    }
    valueAnimator.start()
}

fun <T> Observable<T>.asyncNetworkRequest(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}


fun setHistoriesDate(i: Int): String? {
    val now = Date()
    val calendar = Calendar.getInstance()
    calendar.time = now
    calendar.add(Calendar.DAY_OF_MONTH, -i - 1)
    val date = calendar.time
    val simpleDateFormat = SimpleDateFormat("M/d/yy")
    return simpleDateFormat.format(date)
}


fun convertMsToDate(dateMs: Long): String {

    val persianDate = PersianDate(dateMs)
    val pDateFormatter = PersianDateFormat("Y/m/d")

    val pTimeFormatter = PersianDateFormat("H:m:s")

    return pDateFormatter.format( persianDate) + " ساعت " + pTimeFormatter.format(persianDate);
}

