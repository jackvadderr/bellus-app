package br.sapiens.bellus_app.data.datasource.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetServiceByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseServiceSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toServiceDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetServiceByIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetServiceByIdDataSource {

    override suspend fun get(id: String): State<ServiceDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetServicesByIdDataSourceImpl", "Token disponível")
                Log.d("GetServicesByIdDataSourceImpl", id)

                val url = provider.getBaseUrl().appendPath("services/${id}")
                Log.d("GetServicesByIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetServicesByIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetServicesByIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val responseServiceSchema: ResponseServiceSchema =
                    Json.decodeFromString<ResponseServiceSchema>(responseBody)
                val service: ServiceDTO = responseServiceSchema.toServiceDTO()
                Log.d("GetServicesByIdDataSourceImpl", "Serviços decodificados: $service")

                State.Success(service)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}