package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.GetAppointmentDTO
import br.sapiens.bellus_app.utils.State

fun interface GetAppointmentsByClientIdDataSource {
    suspend fun get(id: String): State<List<GetAppointmentDTO>>
}