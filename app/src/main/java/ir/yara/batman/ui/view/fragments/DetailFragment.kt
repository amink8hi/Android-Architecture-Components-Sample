package ir.yara.batman.ui.view.fragments


import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_detail.*

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

        vm.poster.observe(
            this as LifecycleOwner,
            object : Observer<String?> {
                override fun onChanged(imageurl: String?) {
                    binding?.imageUrl = vm.poster.value
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
        _binding = null

    }

}
