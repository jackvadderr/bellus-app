package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.utils.State

fun interface GetEstablishmentsSummaryDataSource {
    suspend fun getSummaries(): State<List<EstabelecimentoSummaryDTO>>
}