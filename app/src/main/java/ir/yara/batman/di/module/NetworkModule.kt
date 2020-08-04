package ir.yara.batman.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ir.yara.batman.constants.ApiConstants
import ir.yara.batman.network.api.NetworkApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [OkHttpClientModule::class])
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    private lateinit var api: NetworkApi

    @Provides
    fun networkApi(retrofit: Retrofit): NetworkApi {
        api = retrofit.create(NetworkApi::class.java)
        return retrofit.create(NetworkApi::class.java)
    }

    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
