package ir.yara.batman.ui.viewmodel

import android.content.Context
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import ir.yara.batman.R
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel
import ir.yara.batman.ui.base.BaseActivity
import ir.yara.batman.ui.view.fragments.DetailFragment

class MovieItemVM constructor(
    val movieListModel: SearchModel?, private val activityContext: Context
) : ViewModel() {


    fun onClickItem(view: View) {

        (activityContext as BaseActivity).addFragmentNullBackStack(
            DetailFragment().apply {
                this.arguments = bundleOf("imdbID" to movieListModel?.imdbID)
            }, R.string.detail,
            null
        )

    }
}