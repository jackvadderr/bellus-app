package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.utils.State

fun interface GetAppointmentByIdDataSource {
    suspend fun get(id: String): State<AppointmentDTO>
}