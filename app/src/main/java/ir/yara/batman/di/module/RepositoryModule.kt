package ir.yara.batman.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ir.yara.batman.data.db.prefs.DataRepository

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun repositoryProvider(application: Application): DataRepository {
        return DataRepository(application)
    }


}
