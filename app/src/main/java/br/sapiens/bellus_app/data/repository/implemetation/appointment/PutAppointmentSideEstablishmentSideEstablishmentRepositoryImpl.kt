package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.PutAppointmentSideEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PutAppointmentSideEstablishmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutAppointmentSideEstablishmentSideEstablishmentRepositoryImpl @Inject constructor(
    private val dataSource: PutAppointmentSideEstablishmentDataSource,
) : PutAppointmentSideEstablishmentRepository {

    override suspend fun put(schema: PutAppointmentSchemeEncapsulation): State<AppointmentDTO> {
        return try {
            when (val response =
                dataSource.put(schema)) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}