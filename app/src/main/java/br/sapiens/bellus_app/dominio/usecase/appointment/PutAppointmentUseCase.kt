package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PutAppointmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutAppointmentUseCase @Inject constructor(
    private val repository: PutAppointmentRepository
) : UseCase<PutAppointmentSchema, AppointmentDTO>() {
    public override suspend fun invoke(input: PutAppointmentSchema?): State<AppointmentDTO> {
        return try {
            when (val response: State<AppointmentDTO> = repository.put(input!!)) {
                is State.Success -> {
                    val responseAppointment = response.data
                    State.Success(responseAppointment)
                }

                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}