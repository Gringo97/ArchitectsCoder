package oscar.delafuente.arichitectcoders.data.database

import com.oscardelafuente.data.source.LocalDataSource
import com.oscardelafuente.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oscar.delafuente.arichitectcoders.data.toDomainMovie
import oscar.delafuente.arichitectcoders.data.toRoomMovie

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { movieDao.movieCount() <= 0 }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) { movieDao.insertMovies(movies.map { it.toRoomMovie() }) }
    }

    override suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun findById(id: Int): Movie = withContext(Dispatchers.IO) {
        movieDao.findById(id).toDomainMovie()
    }

    override suspend fun update(movie: Movie) {
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) }
    }
}