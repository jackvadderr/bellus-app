package br.sapiens.bellus_app.data.repository.base


import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.utils.State

fun interface PutAppointmentSideEstablishmentRepository {
    suspend fun put(schema: PutAppointmentSchemeEncapsulation): State<AppointmentDTO>
}
