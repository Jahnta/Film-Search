package com.example.film_search.data

import com.example.film_search.data.dto.MoviesSearchRequest
import com.example.film_search.data.dto.MoviesSearchResponse
import com.example.film_search.domain.api.MoviesRepository
import com.example.film_search.domain.models.Movie
import com.example.film_search.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)})
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}