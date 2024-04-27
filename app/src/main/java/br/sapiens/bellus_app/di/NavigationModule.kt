package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.presentation.telas.home.NavControllerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    fun provideNavControllerProvider() = NavControllerProvider()
}