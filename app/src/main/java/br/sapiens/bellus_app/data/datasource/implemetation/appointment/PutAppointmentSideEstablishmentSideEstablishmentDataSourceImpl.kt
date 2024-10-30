package br.sapiens.bellus_app.data.datasource.implemetation.appointment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PutAppointmentSideEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseAppointmentSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toAppointmentDTO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PutAppointmentSideEstablishmentSideEstablishmentDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PutAppointmentSideEstablishmentDataSource {

    override suspend fun put(schema: PutAppointmentSchemeEncapsulation): State<AppointmentDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PutAppointmentDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("appointments/${schema.id}")
                Log.d("PutAppointmentDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PutAppointmentDataSourceImpl", "Método: POST")
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema.schema)
                }
                Log.d("PutAppointmentDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("PutAppointmentDataSourceImpl", "Corpo da resposta: $responseBody")

                val appointmentResponse: ResponseAppointmentSchema =
                    Json.decodeFromString<ResponseAppointmentSchema>(responseBody)

                val appointmentDTO = appointmentResponse.toAppointmentDTO()
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