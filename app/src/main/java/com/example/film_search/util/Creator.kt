package com.example.film_search.util

import android.app.Activity
import android.content.Context
import com.example.film_search.data.MoviesRepositoryImpl
import com.example.film_search.data.network.RetrofitNetworkClient
import com.example.film_search.domain.api.MoviesInteractor
import com.example.film_search.domain.api.MoviesRepository
import com.example.film_search.domain.impl.MoviesInteractorImpl
import com.example.film_search.presentation.movies.MoviesSearchPresenter
import com.example.film_search.presentation.poster.PosterPresenter
import com.example.film_search.presentation.movies.MoviesView
import com.example.film_search.presentation.poster.PosterView

object Creator {

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        moviesView: MoviesView,
        context: Context
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(
            view = moviesView,
            context = context
        )
    }

    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }
}