package ir.yara.batman.ui.view.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.FragmentDetailBinding
import ir.yara.batman.ui.viewmodel.DetailVM
import kotlinx.coroutines.delay

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    lateinit var navController: NavController
    private val vm: DetailVM by viewModels()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.vm = vm
        navController = Navigation.findNavController(view)

        vm.getImdbID(requireArguments().getString("imdbID")!!)

        //marquee
        binding?.title?.isSelected = true
        binding?.year?.isSelected = true
        binding?.rated?.isSelected = true
        binding?.released?.isSelected = true
        binding?.runtime?.isSelected = true
        binding?.genre?.isSelected = true
        binding?.director?.isSelected = true



        vm.poster.observe(
            this as LifecycleOwner,
            object : Observer<String?> {
                override fun onChanged(imageurl: String?) {
                    binding?.imageUrl = vm.poster.value
                    if (vm.poster.value?.length!! > 1) {
                        binding?.img?.visibility = View.VISIBLE
                        binding?.rating?.visibility = View.VISIBLE
                        vm.poster.removeObserver(this)
                    } else {
                        binding?.img?.visibility = View.GONE
                        binding?.rating?.visibility = View.GONE
                    }

                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.lifecycleOwner = null
    }
}
