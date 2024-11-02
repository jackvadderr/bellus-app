package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.utils.State

fun interface PutServiceRepository {
    suspend fun put(schema: PutServiceSchemaEncapsulation): State<ServiceDTO>
}
