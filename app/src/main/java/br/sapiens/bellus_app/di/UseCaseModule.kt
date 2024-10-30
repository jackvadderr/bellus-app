package br.sapiens.bellus_app.di


import br.sapiens.bellus_app.data.repository.base.GetAppointmentByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByClientIdRepository
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByEstablishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetCategoriesByNumberRepository
import br.sapiens.bellus_app.data.repository.base.GetCategoriesListNameRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.data.repository.base.GetProfessionalByUserIdRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsSummaryByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetSearchRepository
import br.sapiens.bellus_app.data.repository.base.GetServiceByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.data.repository.base.PostAppointmentRepository
import br.sapiens.bellus_app.data.repository.base.PostEstablishmentRepository
import br.sapiens.bellus_app.data.repository.base.PostProfessionalRepository
import br.sapiens.bellus_app.data.repository.base.PutAppointmentSideEstablishmentRepository
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByClientIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PostAppointmentUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PutAppointmentSideEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.categories.GetCategoriesByNumberUseCase
import br.sapiens.bellus_app.dominio.usecase.categories.GetCategoriesListNameUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentsSummariesUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.PostEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.login.LoginUseCase
import br.sapiens.bellus_app.dominio.usecase.professional.GetProfessionalByUserIdUseCase
import br.sapiens.bellus_app.dominio.usecase.professional.PostProfessionalUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsSummaryByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.search.GetSearchUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServicesByEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.user.GetUserUseCase
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
    fun provideGetEstablishmentSummariesUseCase(
        getEstablishmentSummariesRepository: GetEstablishmentsSummaryRepository
    ) = GetEstablishmentsSummariesUseCase(getEstablishmentSummariesRepository)

    @ViewModelScoped
    @Provides
    fun provideGetReviewsByEstablishmentIdUseCase(
        repository: GetReviewsByEstalishmentIdRepository
    ) = GetReviewsByEstablishmentIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetReviewsSummaryByEstablishmentIdUseCase(
        repository: GetReviewsSummaryByEstalishmentIdRepository
    ) = GetReviewsSummaryByEstablishmentIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun providePostAppointmentUseCase(
        repository: PostAppointmentRepository
    ) = PostAppointmentUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetAppointmentsByClientIdUseCase(
        repository: GetAppointmentsByClientIdRepository
    ) = GetAppointmentsByClientIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetCategoriesByNumberUseCase(
        repository: GetCategoriesByNumberRepository
    ) = GetCategoriesByNumberUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetServiceeByIdUseCase(
        repository: GetServiceByIdRepository
    ) = GetServiceByIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetCategoriesListNameUseCase(
        repository: GetCategoriesListNameRepository
    ) = GetCategoriesListNameUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetSearchUseCase(
        repository: GetSearchRepository
    ) = GetSearchUseCase(repository)

    @ViewModelScoped
    @Provides
    fun providePostEstablishmentUseCase(
        repository: PostEstablishmentRepository
    ) = PostEstablishmentUseCase(repository)


    @ViewModelScoped
    @Provides
    fun providePutAppointmentUseCase(
        repository: PutAppointmentSideEstablishmentRepository,
    ) = PutAppointmentSideEstablishmentUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetAppointmentsByEstablishmentIdUseCase(
        repository: GetAppointmentsByEstablishmentIdRepository
    ) = GetAppointmentsByEstablishmentIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetProfessionalByUserIdUseCase(
        repository: GetProfessionalByUserIdRepository
    ) = GetProfessionalByUserIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetAppointmentByIdUseCase(
        repository: GetAppointmentByIdRepository
    ) = GetAppointmentByIdUseCase(repository)

    @ViewModelScoped
    @Provides
    fun providePostProfessionalUseCase(
        repository: PostProfessionalRepository
    ) = PostProfessionalUseCase(repository)
}