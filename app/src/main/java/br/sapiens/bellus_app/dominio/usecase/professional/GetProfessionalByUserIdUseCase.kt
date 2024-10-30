package br.sapiens.bellus_app.dominio.usecase.professional

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.data.repository.base.GetProfessionalByUserIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetProfessionalByUserIdUseCase @Inject constructor(
    private val repository: GetProfessionalByUserIdRepository
) : UseCase<String, ProfessionalDTO>() {
    public override suspend fun invoke(input: String?): State<ProfessionalDTO> {
        return try {
            when (val response: State<ProfessionalDTO> = repository.get(input!!)) {
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