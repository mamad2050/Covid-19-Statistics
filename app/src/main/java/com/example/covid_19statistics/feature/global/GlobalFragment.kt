package com.example.covid_19statistics.feature.global

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.data.model.CovidAppEvent
import com.example.covid_19statistics.data.model.Global
import com.example.covid_19statistics.data.model.History
import com.example.covid_19statistics.databinding.FragmentGlobalBinding
import com.example.covid_19statistics.feature.InfoDialog
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.google.android.material.button.MaterialButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class GlobalFragment : CovidAppFragment() {

    private var _binding: FragmentGlobalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GlobalViewModel by viewModel()
    private var histories = ArrayList<History>()
    private var entries = ArrayList<BarEntry>()
    private var deathEntries = ArrayList<BarEntry>()
    private val infoDialog = InfoDialog()
    private lateinit var todayStatistic: Global
    private lateinit var yesterdayStatistic: Global

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlobalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewModel.historyLiveData.observe(viewLifecycleOwner) {


            val jsonObject = JSONObject(it.toString())
            val jsonCases = jsonObject.getJSONObject("cases")
            val jsonDeaths = jsonObject.getJSONObject("deaths")

            for (i in 0 until DAYS_AGO ) {
                val history = History()
                val date: String? = setHistoriesDate(i + 1)
                if (jsonCases.has(date)) {
                    history.cases = jsonCases.getString(date)
                    history.deaths = jsonDeaths.getString(date)
                    history.date = date
                    histories.add(history)
                }
            }

            for (i in 0 until histories.size - 1) {

                histories[i].cases =
                    (histories[i].cases!!.toInt() - histories[i + 1].cases
                    !!.toInt()).toString()

                histories[i].deaths =
                    (histories[i].deaths!!.toInt() - histories[i + 1].deaths
                    !!.toInt()).toString()

                entries.add(
                    BarEntry(
                        DAYS_AGO.toFloat() - i.toFloat(),
                        histories[i].cases!!.toFloat()
                    )
                )
                deathEntries.add(
                    BarEntry(
                        DAYS_AGO.toFloat() - i.toFloat(),
                        histories[i].deaths!!.toFloat()
                    )
                )
            }
            setStatisticsOnViews()
        }

        viewModel.todayLiveData.observe(viewLifecycleOwner) {


            todayStatistic = it


        }

//        viewModel.yesterdayLiveData.observe(viewLifecycleOwner) {
//
//            yesterdayStatistic = it
//
//            setStatisticsOnViews()
//
//        }

        binding.btnInfo.setOnClickListener {

            activity?.let { view -> infoDialog.show(view.supportFragmentManager, null) }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @SuppressLint("SetTextI18n")
    private fun setStatisticsOnViews() {

        binding.tvCasesChart.text = "نمودار مبتلایان $DAYS_AGO روز گذشته "
        binding.tvDeathsChart.text =  "نمودار فوتی های $DAYS_AGO روز گذشته "

        /*set total statistics*/

        valueAnimator(todayStatistic.cases.toString(), binding.tvAllCases)
        valueAnimator(todayStatistic.recovered.toString(), binding.tvAllRecovered)
        valueAnimator(todayStatistic.deaths.toString(), binding.tvAllDeaths)
        binding.tvUpdated.text =
            "${getString(R.string.last_updated_at)} ${convertMsToDate(todayStatistic.updated)}"


        /*set today statistics*/

        valueAnimator(todayStatistic.todayCases.toString(), binding.tvTodayCases)

        valueAnimator(todayStatistic.todayRecovered.toString(), binding.tvTodayRecovered)

        valueAnimator(todayStatistic.todayDeaths.toString(), binding.tvTodayDeaths)

        /*set barchart values */


//        entries.add(BarEntry(daysAgo.toFloat() + 1, yesterdayStatistic.todayCases.toFloat()))
//        deathEntries.add(BarEntry(daysAgo.toFloat() + 1, yesterdayStatistic.todayDeaths.toFloat()))

        entries.add(BarEntry(DAYS_AGO.toFloat() + 1, todayStatistic.todayCases.toFloat()))
        deathEntries.add(BarEntry(DAYS_AGO.toFloat() + 1, todayStatistic.todayDeaths.toFloat()))



        initialBarChart(binding.barchartCases, entries, Color.YELLOW)
        initialBarChart(binding.barchartDeaths, deathEntries, Color.RED)


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(covidAppEvent: CovidAppEvent) {
        when (covidAppEvent.type) {
            CovidAppEvent.Type.CONNECTION_LOST -> {
                val connectionView = showConnectionLost(true)
                connectionView?.findViewById<MaterialButton>(R.id.btn_retry)?.setOnClickListener {
                    showConnectionLost(false)
                    viewModel.showData()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun initialBarChart(barChart: BarChart, entries: ArrayList<BarEntry>, color: Int) {

        val barDataSet = BarDataSet(entries, "Statistics")
        barDataSet.setGradientColor(Color.WHITE, color)
        barDataSet.valueTextSize = CHART_FONT_SIZE.toFloat()
        barDataSet.valueTypeface = binding.tvCountryName.typeface
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        barDataSet.valueFormatter = LargeValueFormatter()

        val barData = BarData(barDataSet)
        barData.barWidth = 0.25f
        barData.setValueTextColor(Color.WHITE)

        barChart.animateY(500)
        barChart.rotation = 360f
        barChart.destroyDrawingCache()
        barChart.setBackgroundResource(R.drawable.bg_chart)
        barChart.data = barData
        val xAxis = barChart.xAxis
        xAxis.isEnabled = false
        val yAxis = barChart.axisLeft
        yAxis.isEnabled = false
        val yAxis2 = barChart.axisRight
        yAxis2.isEnabled = false

        barChart.setDrawBorders(false)
        barChart.setDrawGridBackground(false)
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.isDragEnabled = false
        barChart.setScaleEnabled(false)
        barChart.setPinchZoom(false)
        barChart.isAutoScaleMinMaxEnabled = true

    }

}