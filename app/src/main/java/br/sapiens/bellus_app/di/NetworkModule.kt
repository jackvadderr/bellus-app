package br.sapiens.bellus_app.di

import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideKtorClientProvider(): KtorClientProvider {
        return KtorClientProvider()
    }
}