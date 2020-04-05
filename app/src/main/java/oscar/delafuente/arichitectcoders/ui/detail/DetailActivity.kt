package oscar.delafuente.arichitectcoders.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.ui.common.app
import oscar.delafuente.arichitectcoders.ui.common.getViewModel
import oscar.delafuente.arichitectcoders.ui.common.loadUrl


class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val viewModel by lazy { getViewModel { app.component.detaiViewModel } }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel.model.observe(this, Observer(::updateUi))

        movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(this)

        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(getDrawable(icon))
    }
}
