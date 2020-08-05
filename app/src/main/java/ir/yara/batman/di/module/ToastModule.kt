package ir.yara.batman.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import ir.yara.batman.ui.view.customs.KitToast


@Module
@InstallIn(ActivityComponent::class)
class ToastModule {

    @Provides
    fun toastProvider(@ActivityContext context: Context): KitToast {
        return KitToast(context)
    }
}
