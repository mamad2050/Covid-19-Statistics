package com.example.covid_19statistics.feature.countries

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.CovidAppFragment
import com.example.covid_19statistics.common.EXTRA_KEY_COUNTRY
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.data.CountryFa
import com.example.covid_19statistics.data.CovidAppEvent
import com.example.covid_19statistics.databinding.FragmentCountriesBinding
import com.example.covid_19statistics.feature.detailCountry.CountryDetailActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesFragment : CovidAppFragment(), TextWatcher, CountriesAdapter.ItemClickListener {

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountriesViewModel by viewModel()
    private lateinit var countriesAdapter: CountriesAdapter
    var defaultDialogIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvCountriesFragment.layoutManager = LinearLayoutManager(context)


        binding.btnSort.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogCustom)
                .setSingleChoiceItems(
                    R.array.sortArray, defaultDialogIndex
                ) { dialog, index ->
                    defaultDialogIndex = index
                    countriesAdapter.sortCountries(index)
                    dialog.dismiss()
                }.setTitle(getString(R.string.sortTitle))

            dialog.show()
        }


        binding.etSearch.addTextChangedListener(this)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewModel.countriesLiveData.observe(viewLifecycleOwner) {


            it.forEachIndexed { index, country ->
                CountryFa.getCountriesList().forEach { fa ->
                    if (country.countryInfo.iso3 == fa.iso3) {
                        it[index].name = fa.name
                        CountryFa.getCountriesList().remove(fa)
                    }
                }
            }

            countriesAdapter = CountriesAdapter(it as MutableList<Country>, this)
            binding.rvCountriesFragment.adapter = countriesAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
        countriesAdapter.filter.filter(query)
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun onCountryClick(country: Country) {
        startActivity(Intent(activity, CountryDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_COUNTRY, country)
        })
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

    override fun onResume() {
        super.onResume()
        showConnectionLost(false)
    }

}