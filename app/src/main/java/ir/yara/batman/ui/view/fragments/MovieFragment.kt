package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentMovieBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.viewmodel.MovieVM
import ir.yara.batman.utils.extension.autoCleared
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    private val vm: MovieVM by viewModels()
    var binding by autoCleared<FragmentMovieBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMovieBinding>(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        binding = dataBinding
        binding.lifecycleOwner = viewLifecycleOwner

        return dataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm


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
        vm.adapter.value = null
        activity?.viewModelStore?.clear()
        vm.clearCoroutine()

    }


}
