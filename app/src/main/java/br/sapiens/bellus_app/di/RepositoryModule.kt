package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.base.DeleteServiceDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByClientIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesByNumberDataSource
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesListNameDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.base.GetProfessionalByUserIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsSummaryByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetSearchDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServiceByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.base.PostAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PostEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PostProfessionalDataSource
import br.sapiens.bellus_app.data.datasource.base.PostServiceDataSource
import br.sapiens.bellus_app.data.datasource.base.PutAppointmentSideEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PutEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PutServiceDataSource
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.base.DeleteServiceRepository
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
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
import br.sapiens.bellus_app.data.repository.base.PostServiceRepository
import br.sapiens.bellus_app.data.repository.base.PutAppointmentSideEstablishmentRepository
import br.sapiens.bellus_app.data.repository.base.PutEstablishmentRepository
import br.sapiens.bellus_app.data.repository.base.PutServiceRepository
import br.sapiens.bellus_app.data.repository.implemetation.appointment.GetAppointmentByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.GetAppointmentsByClientIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.GetAppointmentsByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.PostAppointmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.PutAppointmentSideEstablishmentSideEstablishmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.categories.GetCategoriesByNumberRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.categories.GetCategoriesListNameRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetAllEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetEstablishmentByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetEstablishmentsSummariesRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetSearchRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.PostEstablishmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.PutEstablishmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.login.CadastroRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.login.LoginRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.professional.GetProfessionalByUserIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.professional.PostProfessionalRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.review.GetReviewsByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.review.GetReviewsSummaryByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.DeleteServiceRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.GetServiceByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.GetServicesByEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.PostServiceRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.PutServiceRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.user.GetUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {


    @Provides
    fun provideLoginRepository(
        loginDataSource: LoginDataSource,
    ): LoginRepository =
        LoginRepositoryImpl(loginDataSource)

    @Provides
    fun provideRegisterRepository(
        cadastroDataSource: CadastroDataSource,
    ): CadastroRepository =
        CadastroRepositoryImpl(cadastroDataSource)

    @Provides
    fun provideGetUserRepository(
        getUserDataSource: GetUserDataSource,
    ): GetUserRepository =
        GetUserRepositoryImpl(getUserDataSource)

    @Provides
    fun provideGetAllEstablishmentsRepository(
        getAllEstablishmentsDataSource: GetAllEstablishmentsDataSource,
    ): GetAllEstablishmentsRepository =
        GetAllEstablishmentsRepositoryImpl(getAllEstablishmentsDataSource)

    @Provides
    fun provideGetServicesByEstablishmentRepository(
        getServicesByEstablishmentDataSource: GetServicesByEstablishmentDataSource
    ): GetServicesByEstablishmentsRepository =
        GetServicesByEstablishmentsRepositoryImpl(getServicesByEstablishmentDataSource)

    @Provides
    fun provideGetEstablishmentByIdRepository(
        getEstablishmentByIdDataSource: GetEstablishmentByIdDataSource
    ): GetEstablishmentByIdRepository =
        GetEstablishmentByIdRepositoryImpl(getEstablishmentByIdDataSource)

    @Provides
    fun provideGetEstablishmentSummariesRepository(
        getEstablishmentSummarayDataSource: GetEstablishmentsSummaryDataSource
    ): GetEstablishmentsSummaryRepository =
        GetEstablishmentsSummariesRepositoryImpl(getEstablishmentSummarayDataSource)

    @Provides
    fun provideGetReviewsByEstablishmentIdRepository(
        data: GetReviewsByEstablishmentIdDataSource
    ): GetReviewsByEstalishmentIdRepository =
        GetReviewsByEstablishmentIdRepositoryImpl(data)

    @Provides
    fun provideGetReviewsSummaryByEstablishmentIdRepository(
        data: GetReviewsSummaryByEstablishmentIdDataSource
    ): GetReviewsSummaryByEstalishmentIdRepository =
        GetReviewsSummaryByEstablishmentIdRepositoryImpl(data)

    @Provides
    fun providePostAppointmentRepository(
        data: PostAppointmentDataSource
    ): PostAppointmentRepository =
        PostAppointmentRepositoryImpl(data)

    @Provides
    fun provideGetAppointmentByClientIdRepository(
        data: GetAppointmentsByClientIdDataSource
    ): GetAppointmentsByClientIdRepository =
        GetAppointmentsByClientIdRepositoryImpl(data)

    @Provides
    fun provideGetCategoriesByNumberRepository(
        data: GetCategoriesByNumberDataSource
    ): GetCategoriesByNumberRepository =
        GetCategoriesByNumberRepositoryImpl(data)

    @Provides
    fun provideGetServiceByIdRepository(
        data: GetServiceByIdDataSource
    ): GetServiceByIdRepository =
        GetServiceByIdRepositoryImpl(data)

    @Provides
    fun provideGetCategoriesListNameRepository(
        data: GetCategoriesListNameDataSource
    ): GetCategoriesListNameRepository =
        GetCategoriesListNameRepositoryImpl(data)

    @Provides
    fun provideGetSearchRepository(
        data: GetSearchDataSource
    ): GetSearchRepository =
        GetSearchRepositoryImpl(data)

    @Provides
    fun providePostEstablishmentRepository(
        data: PostEstablishmentDataSource
    ): PostEstablishmentRepository =
        PostEstablishmentRepositoryImpl(data)

    @Provides
    fun providePutAppointmentRepository(
        data: PutAppointmentSideEstablishmentDataSource
    ): PutAppointmentSideEstablishmentRepository =
        PutAppointmentSideEstablishmentSideEstablishmentRepositoryImpl(data)

    @Provides
    fun provideGetAppointmentsByEstablishmentIdRepository(
        data: GetAppointmentsByEstablishmentIdDataSource
    ): GetAppointmentsByEstablishmentIdRepository =
        GetAppointmentsByEstablishmentIdRepositoryImpl(data)

    @Provides
    fun provideGetProfessionalByUserIdRepository(
        data: GetProfessionalByUserIdDataSource,
    ): GetProfessionalByUserIdRepository =
        GetProfessionalByUserIdRepositoryImpl(data)

    @Provides
    fun provideGetAppointmentByIdRepository(
        data: GetAppointmentByIdDataSource,
    ): GetAppointmentByIdRepository =
        GetAppointmentByIdRepositoryImpl(data)

    @Provides
    fun providePostProfessionalRepository(
        data: PostProfessionalDataSource
    ): PostProfessionalRepository =
        PostProfessionalRepositoryImpl(data)

    @Provides
    fun providePutEstablishmentRepository(
        data: PutEstablishmentDataSource
    ): PutEstablishmentRepository =
        PutEstablishmentRepositoryImpl(data)

    @Provides
    fun providePostServiceRepository(
        data: PostServiceDataSource
    ): PostServiceRepository =
        PostServiceRepositoryImpl(data)

    @Provides
    fun providePutServiceRepository(
        data: PutServiceDataSource
    ): PutServiceRepository =
        PutServiceRepositoryImpl(data)

    @Provides
    fun provideDeleteServiceRepository(
        data: DeleteServiceDataSource
    ): DeleteServiceRepository =
        DeleteServiceRepositoryImpl(data)
}