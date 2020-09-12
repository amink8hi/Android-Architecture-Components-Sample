package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentDetailBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.viewmodel.DetailVM
import ir.yara.batman.utils.extensions.autoCleared
import javax.inject.Singleton

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    @Singleton
    private val detailViewModel by viewModels<DetailVM>() {
        defaultViewModelProviderFactory
    }
    private var binding by autoCleared<FragmentDetailBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )


        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = detailViewModel

        arguments?.let {
            detailViewModel.getImdbID(it.getString("imdbID")!!)
        }
        detailViewModel.getDetail()

    }

}
