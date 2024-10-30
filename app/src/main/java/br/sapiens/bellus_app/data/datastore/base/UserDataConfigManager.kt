package br.sapiens.bellus_app.data.datastore.base

import android.content.Context
import br.sapiens.bellus_app.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataConfigManager {
    val Context.userData: Flow<UserData>
    suspend fun Context.updateClientData(
        name: String,
        email: String,
        phone: String
    )

    suspend fun Context.updateProfessionalData(
        id: String,
        name: String,
        email: String,
    )
}