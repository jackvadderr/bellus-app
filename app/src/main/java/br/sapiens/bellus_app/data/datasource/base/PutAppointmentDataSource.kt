package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.utils.State

fun interface PutAppointmentDataSource {
    suspend fun put(schema: PutAppointmentSchema): State<AppointmentDTO>
}