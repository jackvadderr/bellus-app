package br.sapiens.bellus_app.data.datasource.implemetation.appointment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByClientIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseAppointmentSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toGetAppointmentDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetAppointmentsByClientIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetAppointmentsByClientIdDataSource {

    override suspend fun get(id: String): State<List<AppointmentDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetAppointmentByClientIdDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("appointments/client/users/${id}/appointments")
                Log.d("GetAppointmentByClientIdDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("GetAppointmentByClientIdDataSourceImpl", "Método: GET")
                val response = client.get(url)
                Log.d("GetAppointmentByClientIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetAppointmentByClientIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val appointmentResponse: List<ResponseAppointmentSchema> =
                    Json.decodeFromString<List<ResponseAppointmentSchema>>(responseBody)
                val appointments: List<AppointmentDTO> = appointmentResponse.map {
                    it.toGetAppointmentDTO()
                }
                State.Success(appointments)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}