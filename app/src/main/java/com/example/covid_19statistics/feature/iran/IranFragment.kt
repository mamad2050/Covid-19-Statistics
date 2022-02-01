package com.example.covid_19statistics.feature.iran

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.data.History
import com.example.covid_19statistics.databinding.FragmentIranBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class IranFragment : CovidAppFragment() {

    private var _binding: FragmentIranBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IranViewModel by viewModel()
    private var histories = ArrayList<History>()
    private var entries = ArrayList<BarEntry>()
    private var deathEntries = ArrayList<BarEntry>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }


        viewModel.historyLiveData.observe(viewLifecycleOwner) {


                val jsonObject = JSONObject(it.toString())
                val jsonDate = jsonObject.getJSONObject("timeline")
                val jsonCases = jsonDate.getJSONObject("cases")
                val jsonDeaths = jsonDate.getJSONObject("deaths")

            for (i in 0..daysAgo.toInt() - 2) {
                val history = History()
                val date: String? = setHistoriesDate(i + 1)
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

        viewModel.iranLiveData.observe(viewLifecycleOwner) {

            valueAnimator(it.cases.toString(), binding.tvAllCases)
            valueAnimator(it.recovered.toString(), binding.tvAllRecovered)
            valueAnimator(it.deaths.toString(), binding.tvAllDeaths)


            binding.tvUpdated.text = "آخرین به روز رسانی در " + convertMsToDate(it.updated)

            if (it.todayCases != null)
                valueAnimator(it.todayCases.toString(), binding.tvTodayCases)
            else
                binding.tvTodayCases.text = context?.getString(R.string.not_declare)

            if (it.todayRecovered != null)
                valueAnimator(it.todayRecovered.toString(), binding.tvTodayRecovered)
            else
                binding.tvTodayRecovered.text = context?.getString(R.string.not_declare)

            if (it.todayDeaths != null)
                valueAnimator(it.todayDeaths.toString(), binding.tvTodayDeaths)
            else
                binding.tvTodayDeaths.text = context?.getString(R.string.not_declare)



            viewModel.iranYesterdayLiveData.observe(viewLifecycleOwner) { yesterday ->

                if (histories[0].cases != yesterday.todayCases.toString()) {
                    entries.add(BarEntry(11f, yesterday.todayCases!!.toFloat()))
                    deathEntries.add(BarEntry(11f, yesterday.todayDeaths!!.toFloat()))

                    if (it.todayCases != null) {
                        entries.add(BarEntry(12f, it.todayCases.toFloat()))
                        deathEntries.add(BarEntry(12f, it.todayDeaths!!.toFloat()))
                    }
                } else {
                    if (it.todayCases != null) {
                        entries.add(BarEntry(11f, it.todayCases.toFloat()))
                        deathEntries.add(BarEntry(11f, it.todayDeaths!!.toFloat()))
                    }
                }

                initialBarChart(binding.barchartCases, entries, Color.YELLOW)

                initialBarChart(binding.barchartDeaths, deathEntries, Color.RED)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initialBarChart(barChart: BarChart, entries: ArrayList<BarEntry>, color: Int) {
        val barDataSet = BarDataSet(entries, "Statistics")
        barDataSet.setGradientColor(Color.WHITE, color)
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
        barDataSet.valueTextSize = 12f
        barDataSet.valueTypeface = binding.tvCountryName.typeface
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        barChart.setDrawBorders(false)
        barChart.setDrawGridBackground(false)
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.isDragEnabled = false
        barChart.setScaleEnabled(false)
        barChart.setPinchZoom(false)
        barChart.isAutoScaleMinMaxEnabled = true

    }}
