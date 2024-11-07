package br.sapiens.bellus_app.di

import android.content.Context
import br.sapiens.bellus_app.dominio.sdk.storage.CoilImageLoaderProvider
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SDKModule {
    @Provides
    @Singleton
    fun provideCoilImageLoaderProvider(
        context: Context
    ) = CoilImageLoaderProvider(context)

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

//    @Provides
//    @Singleton
//    fun provideAuthConfigManager(): AuthConfigManagerImpl {
//        return AuthConfigManagerImpl()
//    }
}
