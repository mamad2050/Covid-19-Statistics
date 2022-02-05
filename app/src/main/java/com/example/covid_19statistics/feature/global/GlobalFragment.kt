package com.example.covid_19statistics.feature.global

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.data.History
import com.example.covid_19statistics.databinding.FragmentGlobalBinding
import com.example.covid_19statistics.feature.InfoDialog
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class GlobalFragment : CovidAppFragment() {

    private var _binding: FragmentGlobalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GlobalViewModel by viewModel()
    private var histories = ArrayList<History>()
    private var entries = ArrayList<BarEntry>()
    private var deathEntries = ArrayList<BarEntry>()
    private val infoDialog = InfoDialog()

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

            for (i in 0 until daysAgo - 1) {
                val history = History()
                val date: String? = setHistoriesDate(i + 2)
                history.cases = jsonCases.getString(date)
                history.deaths = jsonDeaths.getString(date)
                history.date = date
                histories.add(history)
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
                        daysAgo.toFloat() - i.toFloat(),
                        histories[i].cases!!.toFloat()
                    )
                )
                deathEntries.add(
                    BarEntry(
                        daysAgo.toFloat() - i.toFloat(),
                        histories[i].deaths!!.toFloat()
                    )
                )
            }

        }

        viewModel.globalLiveData.observe(viewLifecycleOwner) {

            binding.tvUpdated.text = "آخرین به روز رسانی در " + convertMsToDate(it.updated)

            valueAnimator(it.cases.toString(), binding.tvAllCases)
            valueAnimator(it.recovered.toString(), binding.tvAllRecovered)
            valueAnimator(it.deaths.toString(), binding.tvAllDeaths)
            valueAnimator(it.todayCases.toString(), binding.tvTodayCases)
            valueAnimator(it.todayRecovered.toString(), binding.tvTodayRecovered)
            valueAnimator(it.todayDeaths.toString(), binding.tvTodayDeaths)

            entries.add(BarEntry(daysAgo.toFloat() + 2, it.todayCases.toFloat()))
            deathEntries.add(BarEntry(daysAgo.toFloat() + 2, it.todayDeaths.toFloat()))

        }

        viewModel.yesterdayLiveData.observe(viewLifecycleOwner) { yesterday ->

            entries.add(BarEntry(daysAgo.toFloat() + 1, yesterday.todayCases.toFloat()))

            deathEntries.add(BarEntry(daysAgo.toFloat() + 1, yesterday.todayDeaths.toFloat()))

            initialBarChart(binding.barchartCases, entries, Color.YELLOW)

            initialBarChart(binding.barchartDeaths, deathEntries, Color.RED)

        }

        binding.btnInfo.setOnClickListener {

            activity?.let { view -> infoDialog.show(view.supportFragmentManager, null) }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initialBarChart(barChart: BarChart, entries: ArrayList<BarEntry>, color: Int) {

        val barDataSet = BarDataSet(entries, "Statistics")
        barDataSet.setGradientColor(Color.WHITE, color)
        barDataSet.valueTextSize = 10f
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