package br.sapiens.bellus_app.data.repository.implemetation.services

import br.sapiens.bellus_app.data.datasource.base.GetServiceByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.GetServiceByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetServiceByIdRepositoryImpl @Inject constructor(
    private val dataSource: GetServiceByIdDataSource,
) : GetServiceByIdRepository {

    override suspend fun get(id: String): State<ServiceDTO> {
        return try {
            when (val response =
                dataSource.get(id)) {
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