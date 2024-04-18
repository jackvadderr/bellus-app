package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.viewmodels.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class SplashModule {

    @Provides
    fun provideSplashViewModel(): SplashViewModel {
        return SplashViewModel()
    }
}