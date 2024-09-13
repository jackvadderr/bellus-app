package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.GetAllEstablishmentsDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetUserDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.LoginDataSourceImpl
import br.sapiens.bellus_app.dominio.redux.AuthStore
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {

    @Provides
    fun provideGetAllEstablishmentsDataSource(
        provider: KtorClientProvider
    ): GetAllEstablishmentsDataSource =
        GetAllEstablishmentsDataSourceImpl(provider)

    @Provides
    fun provideGetUserDataSource(
        provider: KtorClientProvider
    ): GetUserDataSourceImpl =
        GetUserDataSourceImpl(provider)

    @Provides
    fun provideLoginDataSource(
        provider: KtorClientProvider,
        authStore: AuthStore
    ): LoginDataSourceImpl =
        LoginDataSourceImpl(authStore, provider)

}