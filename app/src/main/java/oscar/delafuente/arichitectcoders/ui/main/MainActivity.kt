package oscar.delafuente.arichitectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.ui.common.PermissionRequester
import oscar.delafuente.arichitectcoders.ui.common.startActivity
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivity
import oscar.delafuente.arichitectcoders.ui.main.MainViewModel.UiModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester =
        PermissionRequester(
            this,
            ACCESS_COARSE_LOCATION
        )

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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