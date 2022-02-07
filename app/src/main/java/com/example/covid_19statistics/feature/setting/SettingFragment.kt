package com.example.covid_19statistics.feature.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.covid_19statistics.R
import com.example.covid_19statistics.common.daysAgo
import com.example.covid_19statistics.databinding.FragmentIranBinding
import com.example.covid_19statistics.databinding.FragmentSettingBinding
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import it.sephiroth.android.library.numberpicker.doOnStopTrackingTouch

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.setProgress(daysAgo)


        binding.numberPicker.doOnProgressChanged { numberPicker, progress, formUser ->
            daysAgo = progress + 1
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}