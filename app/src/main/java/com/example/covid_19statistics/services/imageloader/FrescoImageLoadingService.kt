package com.example.covid_19statistics.services.imageloader

import com.example.covid_19statistics.view.CovidAppImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingService : ImageLoadingService {

    override fun load(imageView: CovidAppImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else

            throw IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}