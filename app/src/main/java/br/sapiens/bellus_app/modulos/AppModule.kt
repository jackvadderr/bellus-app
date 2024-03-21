package br.sapiens.bellus_app.modulos

import br.sapiens.bellus_app.BellusApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBellusApp(): BellusApp {
        return BellusApp()
    }
}