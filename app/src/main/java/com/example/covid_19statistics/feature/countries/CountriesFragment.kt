package com.example.covid_19statistics.feature.countries

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19statistics.common.CovidAppFragment
import com.example.covid_19statistics.data.Country
import com.example.covid_19statistics.databinding.FragmentCountriesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesFragment : CovidAppFragment() {

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!
    private val countriesViewModel: CountriesViewModel by viewModel()
    private lateinit var countriesAdapter: CountriesAdapter

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


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                countriesAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        countriesViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        countriesViewModel.countriesLiveData.observe(viewLifecycleOwner) {
            countriesAdapter = CountriesAdapter(it as MutableList<Country>)
            binding.rvCountriesFragment.adapter = countriesAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}