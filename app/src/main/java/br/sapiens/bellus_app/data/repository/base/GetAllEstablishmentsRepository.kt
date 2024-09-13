package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.utils.State

fun interface GetAllEstablishmentsRepository {
    suspend fun getAllEstablishments(): State<List<EstabelecimentoDTO>>
}