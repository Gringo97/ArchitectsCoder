package oscar.delafuente.arichitectcoders.data.server


import com.oscardelafuente.data.source.RemoteDataSource
import com.oscardelafuente.domain.Movie
import oscar.delafuente.arichitectcoders.data.toDomainMovie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}