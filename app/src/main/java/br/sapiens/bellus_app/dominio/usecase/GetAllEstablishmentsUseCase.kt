package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAllEstablishmentsUseCase @Inject constructor(
    private val getAllEstablishmentsRepository: GetAllEstablishmentsRepository
) : UseCase<Nothing, List<EstabelecimentoDTO>>() {

    public override suspend fun invoke(input: Nothing?): State<List<EstabelecimentoDTO>> {
        return try {
            when (val response = getAllEstablishmentsRepository.getAllEstablishments()) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}