package br.sapiens.bellus_app.data.datasource.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
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

class GetServicesByEstablishmentDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetServicesByEstablishmentDataSource {

    override suspend fun getServicesByEstablishment(id: String): State<List<ServiceDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetServiceDataSourceImpl", "Token disponível")
                Log.d("GetServiceDataSourceImpl", id)

                val url = provider.getBaseUrl().appendPath("establishments/services/${id}")
                Log.d("GetServiceDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetServiceDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetServiceDataSourceImpl", "Corpo da resposta: $responseBody")

                val responseServiceSchemas: List<ResponseServiceSchema> =
                    Json.decodeFromString<List<ResponseServiceSchema>>(responseBody)
                val services: List<ServiceDTO> = responseServiceSchemas.map { it.toServiceDTO() }
                Log.d("GetServiceDataSourceImpl", "Serviços decodificados: $services")

                State.Success(services)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}