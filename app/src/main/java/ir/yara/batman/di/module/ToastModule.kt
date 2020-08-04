package ir.yara.batman.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.yara.batman.ui.view.customs.KitToast
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ToastModule {

    @Provides
    @Singleton
    fun toastProvider(@ApplicationContext context: Context): KitToast {
        return KitToast(context)
    }
}
