package br.sapiens.bellus_app.dominio.usecase.service

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.GetServiceByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetServiceByIdUseCase @Inject constructor(
    private val repository: GetServiceByIdRepository
) : UseCase<String, ServiceDTO>() {

    public override suspend fun invoke(input: String?): State<ServiceDTO> {
        return try {
            when (val response =
                repository.get(input!!)) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }

    data class Input(
        val establishmentId: String,
    ) : Inputs
}