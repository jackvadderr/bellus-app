package br.sapiens.bellus_app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class SplashModule {

//    @Provides
//    fun provideSplashViewModel(): SplashViewModel {
//        return SplashViewModel(
//            storeConfig = StoreConfig()
//        )
//    }
}