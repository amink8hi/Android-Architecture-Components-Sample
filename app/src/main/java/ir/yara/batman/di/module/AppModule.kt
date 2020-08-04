package ir.yara.batman.di.module

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module()
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideResources(application: Application): Resources {
        return application.resources
    }

}