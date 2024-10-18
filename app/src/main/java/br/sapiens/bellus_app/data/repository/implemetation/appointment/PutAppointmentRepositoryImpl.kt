package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.PutAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PutAppointmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutAppointmentRepositoryImpl @Inject constructor(
    private val dataSource: PutAppointmentDataSource,
) : PutAppointmentRepository {

    override suspend fun put(schema: PutAppointmentSchema): State<AppointmentDTO> {
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