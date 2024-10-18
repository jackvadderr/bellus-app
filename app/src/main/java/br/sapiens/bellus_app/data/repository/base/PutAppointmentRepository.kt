package br.sapiens.bellus_app.data.repository.base


import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.utils.State

fun interface PutAppointmentRepository {
    suspend fun put(schema: PutAppointmentSchema): State<AppointmentDTO>
}
