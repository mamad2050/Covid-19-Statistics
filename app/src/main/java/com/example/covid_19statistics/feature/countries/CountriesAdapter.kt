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
import com.example.covid_19statistics.common.decimalFormatter
import com.example.covid_19statistics.common.implementSpringAnimationTrait
import com.example.covid_19statistics.common.valueAnimator
import com.example.covid_19statistics.data.Country
import kotlin.collections.ArrayList

class CountriesAdapter(
    private var countries: MutableList<Country>,
) : RecyclerView.Adapter<CountriesAdapter.MyHolder>(), Filterable {


    var countryListFiltered = ArrayList<Country>()

    init {
        countryListFiltered = countries as ArrayList<Country>

//        filterCountryList.apply {
//            sortBy { it.todayCases }
//            reverse()
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_countries, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountriesAdapter.MyHolder, position: Int) {
        holder.bindCountry(countryListFiltered[position])
    }

    override fun getItemCount(): Int = countryListFiltered.size

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCountryName = itemView.findViewById<TextView>(R.id.tv_country_name)
        private val ivCountry = itemView.findViewById<ImageView>(R.id.iv_country)
        private val tvPopulation = itemView.findViewById<TextView>(R.id.tv_population)
        private val tvCountryCases = itemView.findViewById<TextView>(R.id.tv_country_cases)
        private val tvCountryRecovered = itemView.findViewById<TextView>(R.id.tv_country_recovered)
        private val tvCountryDeaths = itemView.findViewById<TextView>(R.id.tv_country_deaths)
        private val tvCountryAllCases = itemView.findViewById<TextView>(R.id.tv_country_all_cases)
        private val tvCountryAllRecovered =
            itemView.findViewById<TextView>(R.id.tv_country_all_recovered)
        private val tvCountryAllDeaths = itemView.findViewById<TextView>(R.id.tv_country_all_deaths)

        fun bindCountry(country: Country) {

            tvCountryName.text = country.name
            tvPopulation.text =
                "جمعیت : " + decimalFormatter(country.population.toString()) + " نفر "

            Glide.with(itemView.context).load(country.countryInfo.flag).into(ivCountry)

            if (country.todayCases != null) {
                tvCountryCases.text = country.todayCases.toString()

            } else {
                tvCountryCases.text = itemView.context.getString(R.string.not_declare)
            }

            if (country.todayRecovered != null) {
                tvCountryRecovered.text = country.todayRecovered.toString()
            } else {
                tvCountryRecovered.text = itemView.context.getString(R.string.not_declare)
            }
            if (country.todayDeaths != null) {
                tvCountryDeaths.text = country.todayDeaths.toString()
            } else {
                tvCountryDeaths.text = itemView.context.getString(R.string.not_declare)
            }



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
                val charString = constraint.toString()
                countryListFiltered = if (charString.isEmpty()) countries as ArrayList<Country>
                else{
                    val result = ArrayList<Country>()
                    countries.filter {
                        (it.name.contains(constraint!!))
                    }.forEach { result.add(it) }
                    result
                }

              return  FilterResults().apply { values = countryListFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryListFiltered = if (results?.values==null)
                    ArrayList()
                else
                    results.values as ArrayList<Country>
                notifyDataSetChanged()
            }

        }
    }

    fun sortCountries(index: Int) {

        when (index) {
            0 -> countryListFiltered.apply {
                sortBy { it.todayCases }
                reverse()
            }
            1 -> countryListFiltered.sortBy { it.todayCases }


            2 -> countryListFiltered.apply {
                sortBy { it.todayDeaths }
                reverse()
            }
            3 -> countryListFiltered.sortBy { it.todayDeaths }


            4 -> countryListFiltered.apply {
                sortBy { it.cases }
                reverse()
            }
            5 -> countryListFiltered.sortBy { it.cases }


            6 -> countryListFiltered.apply {
                sortBy { it.deaths }
                reverse()
            }
            7 -> countryListFiltered.sortBy { it.deaths }
        }

        notifyDataSetChanged()


    }



}