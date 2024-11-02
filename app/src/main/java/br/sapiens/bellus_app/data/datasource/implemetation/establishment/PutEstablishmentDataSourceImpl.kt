package br.sapiens.bellus_app.data.datasource.implemetation.establishment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PutEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseEstablishmentSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDTO
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PutEstablishmentDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PutEstablishmentDataSource {

    override suspend fun put(schema: PutEstablishmentSchemaEncapsulation): State<EstabelecimentoDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PutEstablishmentDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("establishments/${schema.id}")
                Log.d("PutEstablishmentDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PutEstablishmentDataSourceImpl", "Método: POST")
                val response = client.put(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema.schema)
                }
                Log.d("PutEstablishmentDataSourceImpl", "Resposta recebida")

                val responseBody: String = response.bodyAsText()
                Log.d("PutEstablishmentDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishmentResponse: ResponseEstablishmentSchema =
                    Json.decodeFromString<ResponseEstablishmentSchema>(responseBody)

                val appointmentDTO = establishmentResponse.toEstablishmentDTO()

                Log.d(
                    "PutEstablishmentDataSourceImpl",
                    "Estabelecimentos decodificados: $appointmentDTO"
                )
                State.Success(appointmentDTO)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}