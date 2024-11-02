package br.sapiens.bellus_app.data.datasource.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.DeleteServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.DeleteSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toDeleteDTO
import io.ktor.client.request.delete
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DeleteServiceDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : DeleteServiceDataSource {

    override suspend fun delete(id: String): State<DeleteDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("RemoveServiceDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("services/${id}")
                Log.d("RemoveServiceDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("RemoveServiceDataSourceImpl", "Método: POST")
                val response = client.delete(url)
                Log.d("RemoveServiceDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("RemoveServiceDataSourceImpl", "Corpo da resposta: $responseBody")

                val serviceResponse: DeleteSchema =
                    Json.decodeFromString<DeleteSchema>(responseBody)

                val deleteDTO: DeleteDTO = serviceResponse.toDeleteDTO()
                Log.d(
                    "RemoveServiceDataSourceImpl",
                    "Estabelecimentos decodificados: $deleteDTO"
                )
                State.Success(deleteDTO)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}