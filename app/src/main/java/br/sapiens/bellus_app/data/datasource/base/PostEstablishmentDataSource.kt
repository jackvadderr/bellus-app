package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.utils.State

fun interface PostEstablishmentDataSource {
    suspend fun post(schema: PostEstablishmentSchema): State<EstabelecimentoDTO>
}