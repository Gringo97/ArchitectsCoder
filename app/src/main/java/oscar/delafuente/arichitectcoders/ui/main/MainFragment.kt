package oscar.delafuente.arichitectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import oscar.delafuente.arichitectcoders.PermissionRequester
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.databinding.FragmentMainBinding
import oscar.delafuente.arichitectcoders.model.server.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.EventObserver
import oscar.delafuente.arichitectcoders.ui.common.app
import oscar.delafuente.arichitectcoders.ui.common.bindingInflate
import oscar.delafuente.arichitectcoders.ui.common.getViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            activity!!,
            ACCESS_COARSE_LOCATION
        )
    }

    private lateinit var navController: NavController
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel = getViewModel { MainViewModel(MoviesRepository(app)) }

        viewModel.navigateToMovie.observe(viewLifecycleOwner, EventObserver { id ->
            navController.navigate(
                R.id.action_mainFragment_to_detailFragment,
                bundleOf("id" to id)
            )
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }
}