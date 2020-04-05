package oscar.delafuente.arichitectcoders.di


import com.oscardelafuente.usecases.FindMovieById
import com.oscardelafuente.usecases.GetPopularMovies
import com.oscardelafuente.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import oscar.delafuente.arichitectcoders.ui.detail.DetailViewModel
import oscar.delafuente.arichitectcoders.ui.main.MainViewModel

@Module
class ViewModelsModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MainViewModel(getPopularMovies)

    @Provides
    fun detailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ): DetailViewModel {
        // TODO the id needs to be provided. We'll see it with scoped graphs
        return DetailViewModel(-1, findMovieById, toggleMovieFavorite)
    }
}