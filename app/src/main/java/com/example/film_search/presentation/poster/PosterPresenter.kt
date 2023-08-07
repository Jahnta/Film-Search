package com.example.film_search.presentation.poster

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.film_search.R

class PosterPresenter(
    private val view: PosterView,
    private val imageUrl: String,
) {

    fun onCreate() {
        view.setupPosterImage(imageUrl)
    }
}