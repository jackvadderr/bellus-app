package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.utils.State

fun interface PutServiceDataSource {
    suspend fun put(schema: PutServiceSchemaEncapsulation): State<ServiceDTO>
}