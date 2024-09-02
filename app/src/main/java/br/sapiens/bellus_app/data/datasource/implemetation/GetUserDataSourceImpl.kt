package br.sapiens.bellus_app.data.datasource.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GetUserDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : GetUserDataSource {

    override suspend fun getUserById(userId: String): State<UserDTO> {
        return try {
            val url = KtorClientProvider.getBaseUrl().appendPath("user")
            val response: UserDTO = httpClient.get(url).body()
            State.Success(response)
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}