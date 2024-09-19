package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.implemetation.GetAllEstablishmentsDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetEstablishmentByIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetEstablishmentSummariesDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetReviewsByEstablishmentIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetServicesByEstablishmentDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.GetUserDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.LoginDataSourceImpl
import br.sapiens.bellus_app.dominio.redux.stores.AuthStore
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
    ): GetUserDataSource =
        GetUserDataSourceImpl(provider)

    @Provides
    fun provideLoginDataSource(
        provider: KtorClientProvider,
        authStore: AuthStore
    ): LoginDataSource =
        LoginDataSourceImpl(authStore, provider)

    @Provides
    fun provideGetServicesByEstablishmentDataSource(
        provider: KtorClientProvider
    ): GetServicesByEstablishmentDataSource =
        GetServicesByEstablishmentDataSourceImpl(provider)

    @Provides
    fun provideGetEstablishmentByIdDataSource(
        provider: KtorClientProvider
    ): GetEstablishmentByIdDataSource =
        GetEstablishmentByIdDataSourceImpl(provider)

    @Provides
    fun provideGetEstablishmentSummary(
        provider: KtorClientProvider
    ): GetEstablishmentsSummaryDataSource =
        GetEstablishmentSummariesDataSourceImpl(provider)

    @Provides
    fun provideGetReviewsByEstablishmentId(
        provider: KtorClientProvider
    ): GetReviewsByEstablishmentIdDataSource =
        GetReviewsByEstablishmentIdDataSourceImpl(provider)
}