package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PostAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.PostAppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseAppointmentSchema
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PostAppointmentDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PostAppointmentDataSource {

    override suspend fun post(schema: RequestAppointmentSchema): State<PostAppointmentDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("appointments/client/")
                Log.d("PostAppointmentDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PostAppointmentDataSourceImpl", "Método: POST")
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema)
                }
                Log.d("PostAppointmentDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("PostAppointmentDataSourceImpl", "Corpo da resposta: $responseBody")

//                val json = Json {
//                    ignoreUnknownKeys = true
//                }

                val appointmentResponse: ResponseAppointmentSchema =
                    Json.decodeFromString<ResponseAppointmentSchema>(responseBody)

                val appointmentDTO = PostAppointmentDTO(
                    appointmentResponse.id,
                    appointmentResponse.user_id,
                    appointmentResponse.establishment_id,
                    appointmentResponse.service_id,
                    appointmentResponse.date,
                    appointmentResponse.status_request,
                )

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