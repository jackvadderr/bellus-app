package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.dominio.sdk.AuthService
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class SplashModule {

    @Provides
    fun provideSplashViewModel(): SplashViewModel {
        return SplashViewModel(
            authService = AuthService(
                firebaseAuth = FirebaseAuth.getInstance()
            )
        )
    }
}