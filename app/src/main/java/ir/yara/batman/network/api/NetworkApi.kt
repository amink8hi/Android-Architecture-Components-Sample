package ir.yara.batman.network.api


import ir.yara.batman.constants.ApiConstants
import ir.yara.batman.data.remote.responce.batmanlist.MovieListModel
import ir.yara.batman.data.remote.responce.detail.DetailListModel
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkApi {


    @GET
    suspend fun GetList(
        @Url suffix: String = ApiConstants.LISTMOVIE
    ): MovieListModel


    @GET
    suspend fun GetDetail(
        @Url imdbID: String
    ): DetailListModel

}