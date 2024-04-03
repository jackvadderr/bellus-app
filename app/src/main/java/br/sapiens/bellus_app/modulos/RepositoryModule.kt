package br.sapiens.bellus_app.modulos

import br.sapiens.bellus_app.repository.LoginRepositoryImpl
import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLoginRepository(): LoginRepositoryImpl {
        return LoginRepositoryImpl { authCredential ->
            try {
                val authResult = FirebaseAuth.getInstance().signInWithCredential(authCredential).await()
                val user = authResult.user
                if (user != null) {
                    State.Success(user)
                } else {
                    State.Error(Exception("User is null"))
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }
    }
}