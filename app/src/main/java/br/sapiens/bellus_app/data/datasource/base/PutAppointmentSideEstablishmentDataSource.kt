package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.utils.State

fun interface PutAppointmentSideEstablishmentDataSource {
    suspend fun put(schema: PutAppointmentSchemeEncapsulation): State<AppointmentDTO>
}