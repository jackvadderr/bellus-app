package br.sapiens.bellus_app.data.datasource.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PutServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseServiceSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toServiceDTO
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PutServiceDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PutServiceDataSource {

    override suspend fun put(schema: PutServiceSchemaEncapsulation): State<ServiceDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PutServiceDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("services/${schema.id}")
                Log.d("PutServiceDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PutServiceDataSourceImpl", "Método: POST")
                val response = client.put(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema.schema)
                }
                Log.d("PutServiceDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("PutServiceDataSourceImpl", "Corpo da resposta: $responseBody")

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