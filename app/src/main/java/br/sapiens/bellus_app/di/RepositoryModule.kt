package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByClientIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesByNumberDataSource
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesListNameDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsSummaryByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetSearchDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServiceByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.base.PostAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PostEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.PutAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.login.CadastroDataSourceImpl
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByClientIdRepository
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByEstablishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetCategoriesByNumberRepository
import br.sapiens.bellus_app.data.repository.base.GetCategoriesListNameRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsSummaryByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetSearchRepository
import br.sapiens.bellus_app.data.repository.base.GetServiceByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.data.repository.base.PostAppointmentRepository
import br.sapiens.bellus_app.data.repository.base.PostEstablishmentRepository
import br.sapiens.bellus_app.data.repository.base.PutAppointmentRepository
import br.sapiens.bellus_app.data.repository.implemetation.appointment.GetAppointmentsByClientIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.GetAppointmentsByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.PostAppointmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.appointment.PutAppointmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.categories.GetCategoriesByNumberRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.categories.GetCategoriesListNameRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetAllEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetEstablishmentByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetEstablishmentsSummariesRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.GetSearchRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.establishment.PostEstablishmentRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.login.CadastroRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.login.LoginRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.review.GetReviewsByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.review.GetReviewsSummaryByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.GetServiceByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.services.GetServicesByEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.user.GetUserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideRegisterDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): CadastroDataSource =
        CadastroDataSourceImpl(firebaseAuth, firebaseFirestore)

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
        data: PutAppointmentDataSource
    ): PutAppointmentRepository =
        PutAppointmentRepositoryImpl(data)

    @Provides
    fun provideGetAppointmentsByEstablishmentIdRepository(
        data: GetAppointmentsByEstablishmentIdDataSource
    ): GetAppointmentsByEstablishmentIdRepository =
        GetAppointmentsByEstablishmentIdRepositoryImpl(data)
}