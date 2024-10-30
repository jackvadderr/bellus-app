package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentByIdUseCase @Inject constructor(
    private val repository: GetAppointmentByIdRepository
) : UseCase<String, AppointmentDTO>() {
    public override suspend fun invoke(input: String?): State<AppointmentDTO> {
        return try {
            when (val response: State<AppointmentDTO> = repository.get(input!!)) {
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