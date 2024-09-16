package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetServicesByEstablishmentsRepositoryImpl @Inject constructor(
    private val getServicesByEstablishmentDataSource: GetServicesByEstablishmentDataSource,
) : GetServicesByEstablishmentsRepository {

    override suspend fun getServicesByEstablishment(id: String): State<List<ServiceDTO>> {
        return try {
            when (val response =
                getServicesByEstablishmentDataSource.getServicesByEstablishment(id)) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}