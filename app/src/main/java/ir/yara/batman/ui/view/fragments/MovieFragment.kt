package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentMovieBinding
import ir.yara.batman.ui.viewmodel.MovieVM
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    lateinit var navController: NavController
    private val vm: MovieVM by viewModels()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.vm = vm

        navController = Navigation.findNavController(view)

        rv_movie.setHasFixedSize(true)
        rv_movie.itemAnimator = DefaultItemAnimator()
        rv_movie.layoutManager = GridLayoutManager(context, 1)

        if (vm.list.value.isNullOrEmpty()) {
            vm.getList()
        } else {
            vm.update()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        vm.adapter.value = null
    }


}
