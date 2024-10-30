package br.sapiens.bellus_app.data.datasource.implemetation.user

import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ClientUserSchema
import br.sapiens.bellus_app.utils.State
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetUserDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetUserDataSource {

    override suspend fun getUserById(): State<UserDTO> {
        return try {
            val url = provider.getBaseUrl().appendPath("users/me")
            val client = provider.client
            val response: HttpResponse = client.get(url)
            val clientUserSchema: ClientUserSchema =
                Json.decodeFromString<ClientUserSchema>(response.body())

            val user = UserDTO(
                id = clientUserSchema.id,
                name = clientUserSchema.name,
                phone = clientUserSchema.phone,
                email = clientUserSchema.email,
                isProfessional = clientUserSchema.isProfessional
            )

            State.Success(user)
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}