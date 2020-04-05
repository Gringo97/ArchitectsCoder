package oscar.delafuente.arichitectcoders.data.server


import com.oscardelafuente.data.source.RemoteDataSource
import com.oscardelafuente.domain.Movie
import oscar.delafuente.arichitectcoders.data.toDomainMovie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        MovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}