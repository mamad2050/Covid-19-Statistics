package com.example.covid_19statistics.feature.countries

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.implementSpringAnimationTrait
import com.example.covid_19statistics.common.valueAnimator
import com.example.covid_19statistics.data.Country
import java.util.*
import kotlin.collections.ArrayList

class CountriesAdapter(
    private var countries: MutableList<Country>,
) : RecyclerView.Adapter<CountriesAdapter.MyHolder>(), Filterable {


    var filterCountryList = ArrayList<Country>()

    init {
        filterCountryList = countries as ArrayList<Country>

        filterCountryList.apply {
            sortBy { it.todayCases }
            reverse()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_countries, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountriesAdapter.MyHolder, position: Int) {
        holder.bindCountry(filterCountryList[position])
    }

    override fun getItemCount(): Int = filterCountryList.size

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCountryName = itemView.findViewById<TextView>(R.id.tv_country_name)
        private val ivCountry = itemView.findViewById<ImageView>(R.id.iv_country)
        private val tvCountryCases = itemView.findViewById<TextView>(R.id.tv_country_cases)
        private val tvCountryRecovered = itemView.findViewById<TextView>(R.id.tv_country_recovered)
        private val tvCountryDeaths = itemView.findViewById<TextView>(R.id.tv_country_deaths)
        private val tvCountryAllCases = itemView.findViewById<TextView>(R.id.tv_country_all_cases)
        private val tvCountryAllRecovered =
            itemView.findViewById<TextView>(R.id.tv_country_all_recovered)
        private val tvCountryAllDeaths = itemView.findViewById<TextView>(R.id.tv_country_all_deaths)

        fun bindCountry(country: Country) {

            tvCountryName.text = country.name

            Glide.with(itemView.context).load(country.countryInfo.flag).into(ivCountry)


            if (country.todayCases != null)
                valueAnimator(country.todayCases.toString(), tvCountryCases)
            else
                tvCountryCases.text = itemView.context.getString(R.string.not_declare)


            if (country.todayRecovered != null)
                valueAnimator(country.todayRecovered.toString(), tvCountryRecovered)
            else
                tvCountryRecovered.text = itemView.context.getString(R.string.not_declare)


            if (country.todayDeaths != null)
                valueAnimator(country.todayDeaths.toString(), tvCountryDeaths)
            else
                tvCountryDeaths.text = itemView.context.getString(R.string.not_declare)


            valueAnimator(country.cases.toString(), tvCountryAllCases)
            valueAnimator(country.recovered.toString(), tvCountryAllRecovered)
            valueAnimator(country.deaths.toString(), tvCountryAllDeaths)

            itemView.implementSpringAnimationTrait()

            itemView.setOnClickListener {

            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterCountryList = if (charSearch.isEmpty()) {
                    countries as ArrayList<Country>
                } else {
                    val resultList = ArrayList<Country>()
                    for (country in countries) {
                        if (country.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(country)
                        }
                    }
                    resultList

                }
                val filterResults = FilterResults()
                filterResults.values = filterCountryList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, p1: FilterResults?) {
                filterCountryList = p1?.values as ArrayList<Country>
                notifyDataSetChanged()
            }

        }
    }

    fun sortCountries(index: Int) {

        when (index) {
            0 -> filterCountryList.apply {
                sortBy { it.todayCases }
                reverse()
            }
            1 -> filterCountryList.sortBy { it.todayCases }


            2 -> filterCountryList.apply {
                sortBy { it.todayDeaths }
                reverse()
            }
            3 -> filterCountryList.sortBy { it.todayDeaths }


            4 -> filterCountryList.apply {
                sortBy { it.cases }
                reverse()
            }
            5 -> filterCountryList.sortBy { it.cases }


            6 -> filterCountryList.apply {
                sortBy { it.deaths }
                reverse()
            }
            7 -> filterCountryList.sortBy { it.deaths }
        }

        notifyDataSetChanged()


    }

}