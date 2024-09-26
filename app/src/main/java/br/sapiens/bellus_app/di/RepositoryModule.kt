package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsSummaryByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.CadastroDataSourceImpl
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetReviewsSummaryByEstalishmentIdRepository
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.data.repository.implemetation.CadastroRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetAllEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetEstablishmentByIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetEstablishmentsSummariesRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetReviewsByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetReviewsSummaryByEstablishmentIdRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetServicesByEstablishmentsRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetUserRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.LoginRepositoryImpl
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
}