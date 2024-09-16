package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.utils.State

fun interface GetServicesByEstablishmentDataSource {
    suspend fun getServicesByEstablishment(id: String): State<List<ServiceDTO>>
}