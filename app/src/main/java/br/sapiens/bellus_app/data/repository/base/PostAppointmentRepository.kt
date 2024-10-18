package br.sapiens.bellus_app.data.repository.base


import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.utils.State

fun interface PostAppointmentRepository {
    suspend fun post(schema: RequestAppointmentSchema): State<AppointmentDTO>
}
