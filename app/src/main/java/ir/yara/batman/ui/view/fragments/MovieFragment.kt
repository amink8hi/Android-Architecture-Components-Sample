package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentMovieBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.view.adapters.MovieAdapter
import ir.yara.batman.ui.viewmodel.MovieVM
import ir.yara.batman.utils.extensions.autoCleared
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Singleton


@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    @Singleton
    private val movieViewModel by viewModels<MovieVM>() {
        defaultViewModelProviderFactory
    }
    private var binding by autoCleared<FragmentMovieBinding>()
    private var adapter = MutableLiveData<MovieAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMovieBinding>(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = movieViewModel

        initRecyclerView(binding.rvMovie)

        movieViewModel.list.observeForever {
            adapter.value = MovieAdapter(movieViewModel.list.value!!)
            adapter.value!!.updateData(it)
            binding.rvMovie.adapter = adapter.value
            adapter.value?.notifyDataSetChanged()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.value = null
        rv_movie.adapter = null

    }


}
