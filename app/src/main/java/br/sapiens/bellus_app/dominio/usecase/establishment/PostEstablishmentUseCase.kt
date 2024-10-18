package br.sapiens.bellus_app.dominio.usecase.establishment

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.PostEstablishmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostEstablishmentUseCase @Inject constructor(
    private val respository: PostEstablishmentRepository,
) : UseCase<PostEstablishmentSchema, EstabelecimentoDTO>() {
    public override suspend fun invoke(input: PostEstablishmentSchema?): State<EstabelecimentoDTO> {
        return try {
            when (val response: State<EstabelecimentoDTO> =
                respository.post(input!!)) {
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