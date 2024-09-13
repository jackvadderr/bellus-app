package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.utils.State

fun interface GetAllEstablishmentsDataSource {
    suspend fun getAllEstablishment(): State<List<EstabelecimentoDTO>>
}