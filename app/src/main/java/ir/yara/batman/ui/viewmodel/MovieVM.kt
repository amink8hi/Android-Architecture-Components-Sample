package ir.yara.batman.ui.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ActivityContext
import ir.yara.batman.data.remote.responce.batmanlist.MovieListModel
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel
import ir.yara.batman.network.api.NetworkApi
import ir.yara.batman.ui.view.adapters.MovieAdapter
import ir.yara.batman.utils.KitLog
import ir.yara.batman.utils.extensions.default
import kotlinx.coroutines.launch


class MovieVM @ViewModelInject constructor(
    private val networkApi: NetworkApi, @ActivityContext private val context: Context
) : ViewModel() {

    var list = MutableLiveData<MutableList<SearchModel?>>().default(mutableListOf())
    var adapter = MutableLiveData<MovieAdapter>()
    var loading = MutableLiveData<Boolean>().default(false)
    var retry = MutableLiveData<Boolean>().default(false)

    fun update() {
        adapter.value = MovieAdapter(list.value, context)
        adapter.value?.notifyDataSetChanged()
    }

    init {
        if (list.value.isNullOrEmpty()) {
            getList()
        } else {
            update()
        }
    }

    fun getList() {
        loading.value = true
        retry.value = false
        viewModelScope.launch {
            try {
                val response = networkApi.GetList()
                handleList(response, response.Search)
            } catch (t: Throwable) {
                KitLog.e(t)
                handleError(t)
            }
        }
    }


    fun retry() {
        getList()
    }


    private fun handleList(baseResponse: MovieListModel, response: MutableList<SearchModel?>) {
        if (baseResponse.responses != "False") {
            try {
                list.value?.addAll(response)
                update()
                loading.value = false
            } catch (e: Exception) {
                KitLog.e(e)
                retry.value = true
                loading.value = false
            }
        } else {
            retry.value = true
            loading.value = false
        }

    }

    private fun handleError(t: Throwable) {
        KitLog.e(t)
        loading.value = false
        retry.value = true
    }


    override fun onCleared() {
        super.onCleared()
        adapter.value = null
    }
}