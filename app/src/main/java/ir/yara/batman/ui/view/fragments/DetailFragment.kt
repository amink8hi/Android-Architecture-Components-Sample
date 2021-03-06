package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.BR
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentDetailBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.viewmodel.DetailVM
import ir.yara.batman.utils.extensions.autoCleared

@AndroidEntryPoint
class DetailFragment() : BaseFragment() {

    private val vm by navGraphViewModels<DetailVM>(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }
    private var binding by autoCleared<FragmentDetailBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).also {
            binding = it
        }.root

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)

        arguments?.let {
            vm.getImdbID(it.getString("imdbID")!!)
        }
        vm.getDetail()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.clear()
    }
}
