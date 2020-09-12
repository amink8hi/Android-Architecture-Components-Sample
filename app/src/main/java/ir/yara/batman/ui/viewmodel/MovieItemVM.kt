package ir.yara.batman.ui.viewmodel

import androidx.lifecycle.ViewModel
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel

class MovieItemVM constructor(
    val movieListModel: SearchModel?
) : ViewModel()