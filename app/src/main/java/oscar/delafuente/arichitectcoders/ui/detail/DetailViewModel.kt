package oscar.delafuente.arichitectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import oscar.delafuente.arichitectcoders.model.database.Movie
import oscar.delafuente.arichitectcoders.model.server.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.ScopedViewModel


class DetailViewModel(private val movieId: Int, private val moviesRepository: MoviesRepository) :
    ScopedViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()

            return _model
        }

    private fun findMovie() = MainScope().launch {
        _model.value = UiModel(moviesRepository.findById(movieId))
    }

    fun onFavoriteClicked() = MainScope().launch {
        _model.value?.movie?.let {
            val updatedMovie = it.copy(favorite = !it.favorite)
            _model.value = UiModel(updatedMovie)
            moviesRepository.update(updatedMovie)
        }
    }
}
