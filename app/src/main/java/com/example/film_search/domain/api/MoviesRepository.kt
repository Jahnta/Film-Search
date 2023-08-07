package com.example.film_search.domain.api

import com.example.film_search.domain.models.Movie
import com.example.film_search.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}