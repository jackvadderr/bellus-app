package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetEstablishmentByIdUseCase @Inject constructor(
    private val repository: GetEstablishmentByIdRepository,
) : UseCase<String, EstabelecimentoDTO>() {
    public override suspend fun invoke(input: String?): State<EstabelecimentoDTO> {
        return try {
            when (val response = repository.getEstablishmentById(input!!)) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}