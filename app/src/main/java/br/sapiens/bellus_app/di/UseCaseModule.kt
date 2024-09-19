package br.sapiens.bellus_app.di


import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.dominio.usecase.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.GetEstablishmentsSummariesUseCase
import br.sapiens.bellus_app.dominio.usecase.GetReviewsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.GetServicesByEstablishmentUseCase
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
        getUserRepository: GetUserRepository,
    ) = GetUserUseCase(getUserRepository)

    @ViewModelScoped
    @Provides
    fun provideGetServicesByEstablishmentUseCase(
        getServicesByEstablishmentsRepository: GetServicesByEstablishmentsRepository
    ) = GetServicesByEstablishmentUseCase(getServicesByEstablishmentsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetEstablishmentByIdUseCase(
        getEstablishmentByIdRepository: GetEstablishmentByIdRepository
    ) = GetEstablishmentByIdUseCase(getEstablishmentByIdRepository)

    @ViewModelScoped
    @Provides
    fun provideGetEstablishmentSummaries(
        getEstablishmentSummariesRepository: GetEstablishmentsSummaryRepository
    ) = GetEstablishmentsSummariesUseCase(getEstablishmentSummariesRepository)

    @ViewModelScoped
    @Provides
    fun provideGetReviewsByEstablishmentId(
        repository: GetReviewsByEstalishmentIdRepository
    ) = GetReviewsByEstablishmentIdUseCase(repository)
}