package br.sapiens.bellus_app.data.datastore.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import br.sapiens.bellus_app.UserData
import br.sapiens.bellus_app.data.datastore.base.UserDataConfigManager
import br.sapiens.bellus_app.data.datastore.model.UserDataConfigSerializer
import kotlinx.coroutines.flow.Flow

object UserDataConfigManagerImpl : UserDataConfigManager {

    private val Context.userPreferencesStore: DataStore<UserData> by dataStore(
        fileName = "user_data.pb",
        serializer = UserDataConfigSerializer,
    )

    override val Context.userData: Flow<UserData> get() = userPreferencesStore.data

    override suspend fun Context.updateClientData(
        name: String,
        email: String,
        phone: String
    ) {
        userPreferencesStore.updateData { currentConfig ->
            currentConfig.toBuilder().apply {
                client = client.toBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setPhone(phone)
                    .build()

            }.build()
        }
    }

    override suspend fun Context.updateProfessionalData(
        id: String,
        name: String,
        email: String,
        cnpj: String
    ) {
        userPreferencesStore.updateData { currentConfig ->
            currentConfig.toBuilder().apply {
                professional = professional.toBuilder()
                    .setId(id)
                    .setName(name)
                    .setEmail(email)
                    .setCnpj(cnpj)
                    .build()

            }.build()
        }
    }

    suspend fun Context.updateClientId(id: String) {
        userPreferencesStore.updateData { currentConfig ->
            currentConfig.toBuilder().apply {
                if (hasClient()) {
                    client = client.toBuilder().setId(id).build()
                }
            }.build()
        }
    }
}