package br.sapiens.bellus_app.data.datastore.base

import android.content.Context
import br.sapiens.bellus_app.AppConfig
import kotlinx.coroutines.flow.Flow


interface AppConfigManager {
    val Context.appConfig: Flow<AppConfig>
    suspend fun Context.updateNotificacoes(notificacoes: Boolean)
    suspend fun Context.updateModoEscuro(modoEscuro: Boolean)
}