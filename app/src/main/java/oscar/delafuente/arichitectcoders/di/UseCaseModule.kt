package oscar.delafuente.arichitectcoders.di

import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.usecases.FindMovieById
import com.oscardelafuente.usecases.GetPopularMovies
import com.oscardelafuente.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}