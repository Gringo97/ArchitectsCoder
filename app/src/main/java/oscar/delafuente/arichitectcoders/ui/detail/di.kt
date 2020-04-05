package oscar.delafuente.arichitectcoders.ui.detail

import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.usecases.FindMovieById
import com.oscardelafuente.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailActivityModule(private val movieId: Int) {

    @Provides
    fun detailViewModelProvider(

        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ): DetailViewModel {
        return DetailViewModel(movieId, findMovieById, toggleMovieFavorite)
    }

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}

@Subcomponent(modules = [(DetailActivityModule::class)])
interface DetailActivityComponent {
    val detaiViewModel: DetailViewModel
}