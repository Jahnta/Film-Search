package com.example.film_search.presentation.movies

import com.example.film_search.domain.models.MoviesState

interface MoviesView {

    // Методы, меняющие внешний вид экрана

    fun render(state: MoviesState)

    fun showToast(additionalMessage: String)

}