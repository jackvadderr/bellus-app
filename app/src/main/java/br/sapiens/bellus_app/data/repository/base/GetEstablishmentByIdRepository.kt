package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.utils.State

fun interface GetEstablishmentByIdRepository {
    suspend fun getEstablishmentById(id: String): State<EstabelecimentoDTO>
}