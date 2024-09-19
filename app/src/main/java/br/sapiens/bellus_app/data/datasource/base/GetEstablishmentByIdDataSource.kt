package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.utils.State

fun interface GetEstablishmentByIdDataSource {
    suspend fun getEstablishmentById(id: String): State<EstabelecimentoDTO>
}