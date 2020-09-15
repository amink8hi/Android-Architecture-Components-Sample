package ir.yara.batman.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.yara.batman.constants.ApiConstants
import ir.yara.batman.data.remote.responce.detail.DetailListModel
import ir.yara.batman.network.api.NetworkApi
import ir.yara.batman.utils.KitLog
import ir.yara.batman.utils.extensions.default
import kotlinx.coroutines.launch


class DetailVM @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var title = MutableLiveData<String>().default("")
    var year = MutableLiveData<String>().default("")
    var rated = MutableLiveData<String>().default("")
    var released = MutableLiveData<String>().default("")
    var runtime = MutableLiveData<String>().default("")
    var genre = MutableLiveData<String>().default("")
    var director = MutableLiveData<String>().default("")
    var rating = MutableLiveData<String>().default("")
    var writer = MutableLiveData<String>().default("")
    var actors = MutableLiveData<String>().default("")
    var plot = MutableLiveData<String>().default("")
    var poster = MutableLiveData<String>().default("")
    var loading = MutableLiveData<Boolean>().default(false)
    var retry = MutableLiveData<Boolean>().default(false)
    var imdbID = MutableLiveData<String>()

    fun getImdbID(id: String) {
        imdbID.value = id
    }

    fun getDetail() {
        loading.value = true
        retry.value = false

        viewModelScope.launch {
            try {
                val response =
                    networkApi.GetDetail(imdbID = ApiConstants.DetailMovie + imdbID.value)
                handleDetail(response)
            } catch (t: Throwable) {
                KitLog.e(t)
                handleError(t)
            }
        }
    }


    fun retry() {
        getDetail()
    }

    private fun handleDetail(response: DetailListModel) {
        if (response.responses != "False") {
            try {
                title.value = "Title: ".plus(response.Title)
                year.value = "Year: ".plus(response.Year)
                rated.value = "Rated: ".plus(response.Rated)
                released.value = "Released: ".plus(response.Released)
                runtime.value = "Runtime: ".plus(response.Runtime)
                genre.value = "Genre: ".plus(response.Genre)
                director.value = "Director: ".plus(response.Director)
                rating.value = response.imdbRating
                writer.value = "Wirters: ".plus(response.Writer)
                actors.value = "Actors: ".plus(response.Actors)
                plot.value = "Plot: ".plus(response.Plot)
                poster.value = response.Poster
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