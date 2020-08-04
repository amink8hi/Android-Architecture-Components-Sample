package ir.yara.batman.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
class OkHttpClientModule {

    @Provides
    internal fun okHttpClient(
        application: Application,
        cache: Cache
    ): OkHttpClient {


        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okhttp = OkHttpClient()
            .newBuilder()
            .cache(cache)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)


        /* // add token in header
         okhttp.addInterceptor { chain ->
             val newRequest = chain.request().newBuilder()
                 .addHeader("Authorization", "Bearer " + DataRepository(application).token)
                 .build()
             chain.proceed(newRequest)

         }*/


        return okhttp.build()
    }

    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10.toLong() * 1024 * 1024) // 10 MB
    }

    @Provides
    fun file(application: Application): File {
        val file = File(application.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

}
