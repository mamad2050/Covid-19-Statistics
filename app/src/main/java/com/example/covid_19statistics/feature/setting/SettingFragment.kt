package com.example.covid_19statistics.feature.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid_19statistics.common.*
import com.example.covid_19statistics.databinding.FragmentSettingBinding

class SettingFragment() : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        binding.numberPicker.setProgress(sharedPreferences.getInt(CHART_DAYS_AGO_KEY, 14))
        binding.sizePicker.setProgress(sharedPreferences.getInt(FONT_SIZE_KEY, 10))

    }

    override fun onDestroy() {
        super.onDestroy()

        DAYS_AGO = binding.numberPicker.progress
        CHART_FONT_SIZE = binding.sizePicker.progress

        sharedPreferences.edit().apply {
            clear()
            putInt(CHART_DAYS_AGO_KEY, DAYS_AGO)
            putInt(FONT_SIZE_KEY, CHART_FONT_SIZE)
        }.apply()

        _binding = null
    }

}