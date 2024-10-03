package br.sapiens.bellus_app.data.datastore.base

import android.content.Context
import br.sapiens.bellus_app.AuthConfig
import kotlinx.coroutines.flow.Flow

interface AuthConfigManager {
    val Context.authConfig: Flow<AuthConfig>
    suspend fun Context.updateClientAuthState(
        isAuthenticated: Boolean,
        userId: String,
        tokenBearer: String
    )
}

//interface AppConfigManager {
//    val Context.appConfig: Flow<AppConfig>
//    suspend fun Context.updateNotificacoes(notificacoes: Boolean)
//    suspend fun Context.updateModoEscuro(modoEscuro: Boolean)
//}