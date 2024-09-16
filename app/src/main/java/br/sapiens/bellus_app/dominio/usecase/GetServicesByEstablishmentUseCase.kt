package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetServicesByEstablishmentUseCase @Inject constructor(
    private val getServicesByEstablishmentsRepository: GetServicesByEstablishmentsRepository
) : UseCase<GetServicesByEstablishmentUseCase.Input, List<ServiceDTO>>() {

    override suspend fun invoke(input: Input?): State<List<ServiceDTO>> {
        return try {
            when (val response =
                getServicesByEstablishmentsRepository.getServicesByEstablishment(input!!.establishmentId)) {
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