package br.sapiens.bellus_app.data.datasource.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PostServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostServiceSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseServiceSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toServiceDTO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PostServiceDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PostServiceDataSource {

    override suspend fun post(schema: PostServiceSchema): State<ServiceDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PostServiceDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("services/")
                Log.d("PostServiceDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PostServiceDataSourceImpl", "Método: POST")
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema)
                }
                Log.d("PostServiceDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("PostServiceDataSourceImpl", "Corpo da resposta: $responseBody")

                val serviceResponse: ResponseServiceSchema =
                    Json.decodeFromString<ResponseServiceSchema>(responseBody)

                val serviceDTO: ServiceDTO = serviceResponse.toServiceDTO()
                Log.d(
                    "PostServiceDataSourceImpl",
                    "Estabelecimentos decodificados: $serviceDTO"
                )
                State.Success(serviceDTO)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}