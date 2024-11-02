package br.sapiens.bellus_app.dominio.usecase.service

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.PutServiceRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutServiceUseCase @Inject constructor(
    private val repository: PutServiceRepository
) : UseCase<PutServiceSchemaEncapsulation, ServiceDTO>() {
    public override suspend fun invoke(input: PutServiceSchemaEncapsulation?): State<ServiceDTO> {
        return try {
            when (val response: State<ServiceDTO> = repository.put(input!!)) {
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