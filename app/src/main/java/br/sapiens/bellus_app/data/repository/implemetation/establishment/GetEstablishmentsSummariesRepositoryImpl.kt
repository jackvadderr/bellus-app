package br.sapiens.bellus_app.data.repository.implemetation.establishment

import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentsSummaryRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetEstablishmentsSummariesRepositoryImpl @Inject constructor(
    private val getEstablishmentsSummaryDataSource: GetEstablishmentsSummaryDataSource
) : GetEstablishmentsSummaryRepository {

    override suspend fun getSummaries(): State<List<EstabelecimentoSummaryDTO>> {
        return try {
            when (val response = getEstablishmentsSummaryDataSource.getSummaries()) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}