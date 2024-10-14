package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.GetAppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByClientIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentsByClientIdUseCase @Inject constructor(
    private val repository: GetAppointmentsByClientIdRepository
) : UseCase<String, List<GetAppointmentDTO>>() {
    public override suspend fun invoke(input: String?): State<List<GetAppointmentDTO>> {
        return try {
            when (val response: State<List<GetAppointmentDTO>> = repository.get(input!!)) {
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