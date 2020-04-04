package oscar.delafuente.arichitectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import oscar.delafuente.arichitectcoders.PermissionRequester
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.databinding.ActivityMainBinding
import oscar.delafuente.arichitectcoders.model.server.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.EventObserver
import oscar.delafuente.arichitectcoders.ui.common.app
import oscar.delafuente.arichitectcoders.ui.common.getViewModel
import oscar.delafuente.arichitectcoders.ui.common.startActivity
import oscar.delafuente.arichitectcoders.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MainViewModel(
                MoviesRepository(app)
            )
        }
        
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter

        viewModel.navigateToMovie.observe(this, EventObserver { id ->
            startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, id)
            }
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })
    }

}