package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.utils.State

fun interface GetServiceByIdRepository {
    suspend fun get(id: String): State<ServiceDTO>
}
