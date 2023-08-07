package com.example.film_search.domain.models

data class MoviesState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val errorMessage: String?
)
