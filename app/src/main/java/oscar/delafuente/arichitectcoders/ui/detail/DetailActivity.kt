package oscar.delafuente.arichitectcoders.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.Movie
import oscar.delafuente.arichitectcoders.ui.common.loadUrl
import java.lang.IllegalStateException

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movie: Movie = intent.getParcelableExtra(MOVIE)
            ?: throw (IllegalStateException("Movie not found"))
        presenter.onCreate(this, movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(movie: Movie) = with(movie) {
        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(this)
    }
}
