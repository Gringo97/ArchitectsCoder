package oscar.delafuente.arichitectcoders.model

import android.app.Application
import oscar.delafuente.arichitectcoders.R

class MoviesRepository(application: Application) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
}