package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.utils.State

fun interface GetServicesByEstablishmentsRepository {
    suspend fun getServicesByEstablishment(id: String): State<List<ServiceDTO>>
}
