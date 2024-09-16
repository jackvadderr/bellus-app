package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.CadastroDataSourceImpl
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.data.repository.implemetation.CadastroRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.GetAllEstablishmentsRepositoryImpl
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
}