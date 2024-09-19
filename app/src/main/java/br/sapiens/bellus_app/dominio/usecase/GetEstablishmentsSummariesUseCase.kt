package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetEstablishmentsSummariesUseCase @Inject constructor(
    private val getEstablishmentsSummaryRepository: GetEstablishmentsSummaryRepository
) : UseCase<Nothing, List<EstabelecimentoSummaryDTO>>() {
    public override suspend fun invoke(input: Nothing?): State<List<EstabelecimentoSummaryDTO>> {
        return try {
            when (val response: State<List<EstabelecimentoSummaryDTO>> =
                getEstablishmentsSummaryRepository.getSummaries()) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}