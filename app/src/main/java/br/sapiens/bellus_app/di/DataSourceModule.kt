package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByClientIdDataSource
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
import br.sapiens.bellus_app.data.datasource.implemetation.appointment.GetAppointmentsByClientIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.appointment.PostAppointmentDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.categories.GetCategoriesByNumberDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.categories.GetCategoriesListNameDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.establishment.GetAllEstablishmentsDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.establishment.GetEstablishmentByIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.establishment.GetEstablishmentSummariesDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.login.LoginDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.review.GetReviewsByEstablishmentIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.review.GetReviewsSummaryByEstablishmentIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.search.GetSearchDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.services.GetServiceByIdDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.services.GetServicesByEstablishmentDataSourceImpl
import br.sapiens.bellus_app.data.datasource.implemetation.user.GetUserDataSourceImpl
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
    fun provideGetEstablishmentSummaryDataSource(
        provider: KtorClientProvider
    ): GetEstablishmentsSummaryDataSource =
        GetEstablishmentSummariesDataSourceImpl(provider)

    @Provides
    fun provideGetReviewsByEstablishmentIdDataSource(
        provider: KtorClientProvider
    ): GetReviewsByEstablishmentIdDataSource =
        GetReviewsByEstablishmentIdDataSourceImpl(provider)

    @Provides
    fun provideGetReviewsSummaryByEstablishmentIdDataSource(
        provider: KtorClientProvider
    ): GetReviewsSummaryByEstablishmentIdDataSource =
        GetReviewsSummaryByEstablishmentIdDataSourceImpl(provider)

    @Provides
    fun providePostAppointmentDataSource(
        provider: KtorClientProvider
    ): PostAppointmentDataSource =
        PostAppointmentDataSourceImpl(provider)

    @Provides
    fun provideGetAppointmentByIdDataSource(
        provider: KtorClientProvider
    ): GetAppointmentsByClientIdDataSource =
        GetAppointmentsByClientIdDataSourceImpl(provider)

    @Provides
    fun provideGetCategoriesByNumberDataSource(
        provider: KtorClientProvider
    ): GetCategoriesByNumberDataSource =
        GetCategoriesByNumberDataSourceImpl(provider)

    @Provides
    fun provideGetServiceByIdDataSource(
        provider: KtorClientProvider
    ): GetServiceByIdDataSource =
        GetServiceByIdDataSourceImpl(provider)

    @Provides
    fun provideGetCategoriesListNameDataSource(
        provider: KtorClientProvider
    ): GetCategoriesListNameDataSource =
        GetCategoriesListNameDataSourceImpl(provider)

    @Provides
    fun provideGetSearchDataSource(
        provider: KtorClientProvider
    ): GetSearchDataSource =
        GetSearchDataSourceImpl(provider)
}