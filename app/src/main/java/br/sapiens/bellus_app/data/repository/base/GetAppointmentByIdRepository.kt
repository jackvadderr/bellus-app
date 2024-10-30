package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.utils.State

fun interface GetAppointmentByIdRepository {
    suspend fun get(id: String): State<AppointmentDTO>
}
