package oscar.delafuente.arichitectcoders.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_detail.*
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.Movie
import oscar.delafuente.arichitectcoders.ui.common.getViewModel
import oscar.delafuente.arichitectcoders.ui.common.loadUrl


class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie: Movie = intent.getParcelableExtra(MOVIE)
            ?: throw (IllegalStateException("Movie not found"))

        viewModel = getViewModel { DetailViewModel(movie) }

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(this)
    }
}
