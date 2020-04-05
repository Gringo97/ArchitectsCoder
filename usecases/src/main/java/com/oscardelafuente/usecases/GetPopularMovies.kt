package com.oscardelafuente.usecases

import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}