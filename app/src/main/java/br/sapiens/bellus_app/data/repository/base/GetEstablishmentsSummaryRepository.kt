package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.utils.State

fun interface GetEstablishmentsSummaryRepository {
    suspend fun getSummaries(): State<List<EstabelecimentoSummaryDTO>>
}