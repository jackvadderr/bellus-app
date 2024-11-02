package br.sapiens.bellus_app.dominio.usecase.service

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.data.repository.base.DeleteServiceRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class DeleteServiceUseCase @Inject constructor(
    private val repository: DeleteServiceRepository
) : UseCase<String, DeleteDTO>() {
    public override suspend fun invoke(input: String?): State<DeleteDTO> {
        return try {
            when (val response: State<DeleteDTO> = repository.delete(input!!)) {
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