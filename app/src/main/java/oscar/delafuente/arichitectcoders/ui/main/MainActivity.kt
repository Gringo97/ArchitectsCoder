package oscar.delafuente.arichitectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.oscardelafuente.data.repository.MoviesRepository
import com.oscardelafuente.data.repository.RegionRepository
import com.oscardelafuente.usecases.GetPopularMovies
import kotlinx.android.synthetic.main.activity_main.*
import oscar.delafuente.arichitectcoders.ui.common.PermissionRequester
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.data.AndroidPermissionChecker
import oscar.delafuente.arichitectcoders.data.PlayServicesLocationDataSource
import oscar.delafuente.arichitectcoders.data.database.RoomDataSource
import oscar.delafuente.arichitectcoders.data.server.TheMovieDbDataSource
import oscar.delafuente.arichitectcoders.ui.common.app
import oscar.delafuente.arichitectcoders.ui.common.getViewModel
import oscar.delafuente.arichitectcoders.ui.common.startActivity
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivity
import oscar.delafuente.arichitectcoders.ui.main.MainViewModel.UiModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester =
        PermissionRequester(
            this,
            ACCESS_COARSE_LOCATION
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel {
            MainViewModel(
                GetPopularMovies(
                    MoviesRepository(
                        RoomDataSource(app.db),
                        TheMovieDbDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        app.getString(R.string.api_key)
                    )
                )
            )
        }


        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }

        }
    }
}