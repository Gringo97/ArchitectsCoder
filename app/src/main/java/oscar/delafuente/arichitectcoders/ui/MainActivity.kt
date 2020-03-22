package oscar.delafuente.arichitectcoders.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.CoroutineScopeActivity

class MainActivity : CoroutineScopeActivity() {


    private val moviesRepository: MoviesRepository by lazy { MoviesRepository(this) }


    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter

        launch {
            adapter.movies = moviesRepository.findPopularMovies().results
        }
    }

}
