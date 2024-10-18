package br.sapiens.bellus_app.data.datasource.implemetation.establishment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PostEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseEstablishmentSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDTO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PostEstablishmentDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PostEstablishmentDataSource {

    override suspend fun post(schema: PostEstablishmentSchema): State<EstabelecimentoDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PostAppointmentDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("establishments/")
                Log.d("PostAppointmentDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PostAppointmentDataSourceImpl", "Método: POST")
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema)
                }
                Log.d("PostAppointmentDataSourceImpl", "Resposta recebida")

                val responseBody: String = response.bodyAsText()
                Log.d("PostAppointmentDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishmentResponse: ResponseEstablishmentSchema =
                    Json.decodeFromString<ResponseEstablishmentSchema>(responseBody)

                val appointmentDTO = establishmentResponse.toEstablishmentDTO()

                Log.d(
                    "PostAppointmentDataSourceImpl",
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