package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.utils.State

fun interface GetServiceByIdDataSource {
    suspend fun get(id: String): State<ServiceDTO>
}