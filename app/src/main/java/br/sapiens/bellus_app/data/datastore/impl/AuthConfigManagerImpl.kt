package br.sapiens.bellus_app.data.datastore.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import br.sapiens.bellus_app.AuthConfig
import br.sapiens.bellus_app.data.datastore.base.AuthConfigManager
import br.sapiens.bellus_app.data.datastore.model.AuthConfigSerializer
import kotlinx.coroutines.flow.Flow

object AuthConfigManagerImpl : AuthConfigManager {

    private val Context.userPreferencesStore: DataStore<AuthConfig> by dataStore(
        fileName = "auth_config.pb",
        serializer = AuthConfigSerializer,
    )

    override val Context.authConfig: Flow<AuthConfig> get() = userPreferencesStore.data

    override suspend fun Context.updateAuthState(
        isAuthenticated: Boolean,
        userId: String,
        tokenBearer: String
    ) {
        userPreferencesStore.updateData { currentConfig ->
            currentConfig.toBuilder()
                .setIsAuthenticated(isAuthenticated)
                .setUserId(userId)
                .setTokenBearer(tokenBearer)
                .build()
        }
    }
}