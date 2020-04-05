package com.oscardelafuente.usecases


import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}