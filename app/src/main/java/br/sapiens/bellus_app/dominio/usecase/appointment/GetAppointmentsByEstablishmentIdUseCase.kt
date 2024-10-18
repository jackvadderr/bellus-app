package br.sapiens.bellus_app.dominio.usecase.appointment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByEstablishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentsByEstablishmentIdUseCase @Inject constructor(
    private val repository: GetAppointmentsByEstablishmentIdRepository,
) : UseCase<String, List<AppointmentDTO>>() {
    public override suspend fun invoke(input: String?): State<List<AppointmentDTO>> {
        return try {
            when (val response: State<List<AppointmentDTO>> = repository.get(input!!)) {
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