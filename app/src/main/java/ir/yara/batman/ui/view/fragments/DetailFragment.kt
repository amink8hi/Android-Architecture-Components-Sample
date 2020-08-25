package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentDetailBinding
import ir.yara.batman.ui.base.BaseFragment
import ir.yara.batman.ui.viewmodel.DetailVM
import ir.yara.batman.utils.extension.autoCleared
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val vm: DetailVM by viewModels()
    var binding by autoCleared<FragmentDetailBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        binding = dataBinding
        binding.lifecycleOwner = viewLifecycleOwner

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        vm.getImdbID(requireArguments().getString("imdbID")!!)

        vm.poster.observe(
            this as LifecycleOwner,
            object : Observer<String?> {
                override fun onChanged(imageurl: String?) {
                    binding.imageUrl = vm.poster.value
                    if (vm.poster.value?.length!! > 1) {
                        img.visibility = View.VISIBLE
                        rating.visibility = View.VISIBLE
                        vm.poster.removeObserver(this)
                    } else {
                        img.visibility = View.GONE
                        rating.visibility = View.GONE
                    }

                }
            })

        vm.getDetail()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        activity?.viewModelStore?.clear()
    }

}
