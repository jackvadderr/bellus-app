package br.sapiens.bellus_app.data.repository.implemetation

import android.content.Context
import br.sapiens.bellus_app.AuthConfig
import br.sapiens.bellus_app.data.datastore.impl.AuthConfigManagerImpl
import br.sapiens.bellus_app.data.repository.base.AuthConfigRepository
import kotlinx.coroutines.flow.Flow

class AuthConfigRepositoryImpl(private val context: Context) : AuthConfigRepository {
    override val authConfig: Flow<AuthConfig> get() = AuthConfigManagerImpl.run { context.authConfig }

    override suspend fun updateAuthState(isAuthenticated: Boolean, userId: String, email: String) {
        AuthConfigManagerImpl.run { context.updateClientAuthState(isAuthenticated, userId, email) }
    }
}