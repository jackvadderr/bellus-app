package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.base.RegisterDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.GetUserDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.LoginDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.RegisterDataSourceImpl
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.data.repository.base.RegisterRepository
import br.sapiens.bellus_app.data.repository.implemetation.GetUserRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.LoginRepositoryImpl
import br.sapiens.bellus_app.data.repository.implemetation.RegisterRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideLoginDataSource(
    ): LoginDataSource =
        LoginDataSourceImpl()

    @Provides
    fun provideLoginRepository(
        loginDataSource: LoginDataSource,
    ): LoginRepository =
        LoginRepositoryImpl(loginDataSource)

    @Provides
    fun provideRegisterDataSource(
        firebaseFirestore: FirebaseFirestore,
    ): RegisterDataSource =
        RegisterDataSourceImpl(firebaseFirestore)

    @Provides
    fun provideRegisterRepository(
        registerDataSource: RegisterDataSource,
    ): RegisterRepository =
        RegisterRepositoryImpl(registerDataSource)

    @Provides
    fun provideGetUserDataSource(
        firebaseFirestore: FirebaseFirestore,
    ): GetUserDataSource =
        GetUserDataSourceImpl(firebaseFirestore)

    @Provides
    fun provideGetUserRepository(
        getUserDataSource: GetUserDataSource,
    ): GetUserRepository =
        GetUserRepositoryImpl(getUserDataSource)

}