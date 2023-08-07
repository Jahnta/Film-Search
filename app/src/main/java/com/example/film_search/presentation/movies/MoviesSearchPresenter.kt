package com.example.film_search.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.film_search.R
import com.example.film_search.domain.api.MoviesInteractor
import com.example.film_search.domain.models.Movie
import com.example.film_search.domain.models.MoviesState
import com.example.film_search.util.Creator

class MoviesSearchPresenter(
    private val view: MoviesView,
    private val context: Context
) {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())
    private val movies = ArrayList<Movie>()

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            view.render(
                MoviesState(
                    movies = movies,
                    isLoading = true,
                    errorMessage = null
                )
            )

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {
                                view.render(
                                    MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        errorMessage = context.getString(R.string.something_went_wrong)
                                    )
                                )
                            }

                            movies.isEmpty() -> {
                                view.render(
                                    MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        errorMessage = context.getString(R.string.nothing_found)
                                    )
                                )
                            }

                            else -> {
                                view.render(
                                    MoviesState(
                                        movies = movies,
                                        isLoading = false,
                                        errorMessage = null
                                    )
                                )
                            }
                        }

                    }
                }
            })
        }
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

}