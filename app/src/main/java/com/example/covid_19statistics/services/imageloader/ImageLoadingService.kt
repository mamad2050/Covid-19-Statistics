package com.example.covid_19statistics.services.imageloader

import com.example.covid_19statistics.view.CovidAppImageView


interface ImageLoadingService {

    fun load(imageView: CovidAppImageView, imageUrl: String)
}