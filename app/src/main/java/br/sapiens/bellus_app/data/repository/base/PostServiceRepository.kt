package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostServiceSchema
import br.sapiens.bellus_app.utils.State

fun interface PostServiceRepository {
    suspend fun post(schema: PostServiceSchema): State<ServiceDTO>
}
