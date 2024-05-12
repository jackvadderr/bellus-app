package br.sapiens.bellus_app.data.datastore.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import br.sapiens.bellus_app.AppConfig
import br.sapiens.bellus_app.data.datastore.base.AppConfigManager
import br.sapiens.bellus_app.data.datastore.model.AppConfigSerializer
import kotlinx.coroutines.flow.Flow


object AppConfigManagerImpl: AppConfigManager {

    private val Context.userPreferencesStore: DataStore<AppConfig> by dataStore(
        fileName = "app_config.pb",
        serializer = AppConfigSerializer,
    )

    override val Context.appConfig: Flow<AppConfig> get() = userPreferencesStore.data

    override suspend fun Context.updateNotificacoes(notificacoes: Boolean) {
        userPreferencesStore.updateData {
            currentConfig ->
            currentConfig.toBuilder()
                .setNotificacoes(notificacoes)
                .build()
        }
    }

    override suspend fun Context.updateModoEscuro(modoEscuro: Boolean) {
        userPreferencesStore.updateData {
            currentConfig ->
            currentConfig.toBuilder()
                .setModoEscuro(modoEscuro)
                .build()
        }
    }
}