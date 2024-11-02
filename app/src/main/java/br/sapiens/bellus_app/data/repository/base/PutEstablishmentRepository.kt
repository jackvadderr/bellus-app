package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.utils.State

fun interface PutEstablishmentRepository {
    suspend fun put(schema: PutEstablishmentSchemaEncapsulation): State<EstabelecimentoDTO>
}
