package ir.yara.batman.ui.view.activities

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.ui.base.BaseActivity

@AndroidEntryPoint
class MovieActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

    }


}