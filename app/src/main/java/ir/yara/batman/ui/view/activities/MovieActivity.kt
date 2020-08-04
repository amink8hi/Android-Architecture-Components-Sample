package ir.yara.batman.ui.view.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.databinding.ActivityMovieBinding
import ir.yara.batman.ui.base.BaseActivity

@AndroidEntryPoint
class MovieActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        binding.lifecycleOwner = this


    }


}