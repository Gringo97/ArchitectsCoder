package com.oscardelafuente.usecases

import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}