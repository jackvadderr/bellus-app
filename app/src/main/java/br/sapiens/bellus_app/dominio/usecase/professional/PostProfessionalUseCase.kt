package br.sapiens.bellus_app.dominio.usecase.professional

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.data.repository.base.PostProfessionalRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostProfessionalUseCase @Inject constructor(
    private val repository: PostProfessionalRepository
) : UseCase<PostProfessionalSchema, ProfessionalDTO>() {
    public override suspend fun invoke(input: PostProfessionalSchema?): State<ProfessionalDTO> {
        return try {
            when (val response: State<ProfessionalDTO> = repository.post(input!!)) {
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