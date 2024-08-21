package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.AuthConfig
import kotlinx.coroutines.flow.Flow

interface AuthConfigRepository {
    val authConfig: Flow<AuthConfig>
    suspend fun updateAuthState(isAuthenticated: Boolean, userId: String, email: String)
}