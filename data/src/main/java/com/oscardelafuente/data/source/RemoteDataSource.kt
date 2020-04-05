package com.oscardelafuente.data.source

import com.oscardelafuente.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}