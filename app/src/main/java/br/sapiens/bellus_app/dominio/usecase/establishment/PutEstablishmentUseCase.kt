package br.sapiens.bellus_app.dominio.usecase.establishment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.PutEstablishmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutEstablishmentUseCase @Inject constructor(
    private val respository: PutEstablishmentRepository,
) : UseCase<PutEstablishmentSchemaEncapsulation, EstabelecimentoDTO>() {
    public override suspend fun invoke(input: PutEstablishmentSchemaEncapsulation?): State<EstabelecimentoDTO> {
        return try {
            when (val response: State<EstabelecimentoDTO> =
                respository.put(input!!)) {
                is State.Success -> {
                    val establishment = response.data
                    State.Success(establishment)
                }

                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}