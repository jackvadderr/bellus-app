package br.sapiens.bellus_app.data.datasource.implemetation.appointment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetAppointmentByIdDataSource
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

class GetAppointmentByIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetAppointmentByIdDataSource {

    override suspend fun get(id: String): State<AppointmentDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetAppointmentByIdDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("appointments/client/$id")
                Log.d("GetAppointmentByIdDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("GetAppointmentByIdDataSourceImpl", "Método: GET")
                val response = client.get(url)
                Log.d("GetAppointmentByIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetAppointmentByIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val appointmentResponse: ResponseAppointmentSchema =
                    Json.decodeFromString<ResponseAppointmentSchema>(responseBody)
                val appointment: AppointmentDTO = appointmentResponse.toGetAppointmentDTO()
                State.Success(appointment)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}