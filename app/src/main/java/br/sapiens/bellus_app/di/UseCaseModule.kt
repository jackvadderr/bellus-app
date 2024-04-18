package br.sapiens.bellus_app.di


import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.dominio.sdk.AuthService
import br.sapiens.bellus_app.dominio.usecase.GetUserUseCase
import br.sapiens.bellus_app.dominio.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideLoginUseCase(
        loginRepository: LoginRepository,
    ) = LoginUseCase(loginRepository)

    @ViewModelScoped
    @Provides
    fun provideGetUserUseCase(
        authService: AuthService,
        getUserRepository: GetUserRepository,
    ) = GetUserUseCase(authService, getUserRepository)

}