package oscar.delafuente.arichitectcoders.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.databinding.FragmentDetailBinding
import oscar.delafuente.arichitectcoders.model.server.MoviesRepository
import oscar.delafuente.arichitectcoders.ui.common.app
import oscar.delafuente.arichitectcoders.ui.common.bindingInflate
import oscar.delafuente.arichitectcoders.ui.common.getViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = getViewModel {
            DetailViewModel(args.id, MoviesRepository(app))
        }

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }
    }
}
