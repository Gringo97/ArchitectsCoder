package oscar.delafuente.arichitectcoders.ui.main


import kotlinx.coroutines.launch
import oscar.delafuente.arichitectcoders.model.Movie
import oscar.delafuente.arichitectcoders.model.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.Scope

class MainPresenter(private val moviesRepository: MoviesRepository) : Scope by Scope.Impl() {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(movies: List<Movie>)
        fun navigateTo(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        initScope()
        this.view = view

        launch {
            view.showProgress()
            view.updateData(moviesRepository.findPopularMovies().results)
            view.hideProgress()
        }
    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }

    fun onDestroy() {
        this.view = null
        destroyScope()
    }
}