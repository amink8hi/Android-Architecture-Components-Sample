package ir.yara.batman.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.yara.batman.data.remote.responce.batmanlist.MovieListModel
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel
import ir.yara.batman.network.api.NetworkApi
import ir.yara.batman.utils.KitLog
import ir.yara.batman.utils.extensions.default
import kotlinx.coroutines.launch


class MovieVM @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var list = MutableLiveData<MutableList<SearchModel>>().default(mutableListOf())
    var loading = MutableLiveData<Boolean>().default(false)
    var retry = MutableLiveData<Boolean>().default(false)


    init {
        getList()
    }


    fun getList() {
        loading.value = true
        retry.value = false
        if (list.value.isNullOrEmpty()) {
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
    }


    fun retry() {
        getList()
    }


    private fun handleList(baseResponse: MovieListModel, response: MutableList<SearchModel>) {
        if (baseResponse.responses != "False") {
            try {
                list.value?.addAll(response)
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

    fun clear() = onCleared()

    override fun onCleared() {

    }
}