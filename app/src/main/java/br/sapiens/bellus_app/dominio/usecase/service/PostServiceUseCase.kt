package br.sapiens.bellus_app.dominio.usecase.service

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.PostServiceRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostServiceSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostServiceUseCase @Inject constructor(
    private val repository: PostServiceRepository
) : UseCase<PostServiceSchema, ServiceDTO>() {
    public override suspend fun invoke(input: PostServiceSchema?): State<ServiceDTO> {
        return try {
            when (val response: State<ServiceDTO> = repository.post(input!!)) {
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