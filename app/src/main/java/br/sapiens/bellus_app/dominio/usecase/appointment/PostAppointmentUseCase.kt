package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PostAppointmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostAppointmentUseCase @Inject constructor(
    private val repository: PostAppointmentRepository
) : UseCase<RequestAppointmentSchema, AppointmentDTO>() {
    public override suspend fun invoke(input: RequestAppointmentSchema?): State<AppointmentDTO> {
        return try {
            when (val response: State<AppointmentDTO> = repository.post(input!!)) {
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