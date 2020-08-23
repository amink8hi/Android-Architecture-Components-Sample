package ir.yara.batman.ui.viewmodel

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import ir.yara.batman.R
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel

class MovieItemVM constructor(val movieListModel: SearchModel?, val view: View) {

    fun onClickItem() {
        val bundle = bundleOf(
            "imdbID" to movieListModel?.imdbID
        )
        view.findNavController().navigate(R.id.action_movieFragment_to_DetailFragment, bundle)
    }
}