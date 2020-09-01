package ir.yara.batman.ui.view.activities

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint
import ir.yara.batman.R
import ir.yara.batman.ui.base.BaseActivity
import ir.yara.batman.ui.view.fragments.MovieFragment
import ir.yara.batman.utils.KitLog

@AndroidEntryPoint
class MovieActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        replaceFragment(MovieFragment(), R.string.movie, null)
        backsStackHandler()

    }


    private fun backsStackHandler() {
        val backStackListener = FragmentManager.OnBackStackChangedListener {
            val position = supportFragmentManager.backStackEntryCount
            try {
                val backStack = kitBackStack.value!!.subList(0, position)
                kitBackStack.value = backStack
            } catch (e: Exception) {
                KitLog.e(e)
            }
        }
        supportFragmentManager.addOnBackStackChangedListener(backStackListener)
    }


}