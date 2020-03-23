package oscar.delafuente.arichitectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.Movie
import oscar.delafuente.arichitectcoders.model.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.startActivity
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy { MainPresenter(MoviesRepository(this)) }
    private val adapter by lazy { MoviesAdapter(presenter::onMovieClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate(this)
        recycler.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, movie)
        }
    }
}