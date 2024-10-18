package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.utils.State

fun interface PostAppointmentDataSource {
    suspend fun post(schema: RequestAppointmentSchema): State<AppointmentDTO>
}