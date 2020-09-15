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
import ir.yara.batman.databinding.FragmentLoginBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.viewmodel.LoginVM
import ir.yara.batman.utils.extensions.autoCleared


@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val vm by navGraphViewModels<LoginVM>(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }
    private var binding by autoCleared<FragmentLoginBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).also {
            binding = it
        }.root

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.clear()
    }
}
