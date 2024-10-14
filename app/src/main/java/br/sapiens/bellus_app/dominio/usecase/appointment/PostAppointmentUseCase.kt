package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.PostAppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PostAppointmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostAppointmentUseCase @Inject constructor(
    private val repository: PostAppointmentRepository
) : UseCase<RequestAppointmentSchema, PostAppointmentDTO>() {
    public override suspend fun invoke(input: RequestAppointmentSchema?): State<PostAppointmentDTO> {
        return try {
            when (val response: State<PostAppointmentDTO> = repository.post(input!!)) {
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