package br.sapiens.bellus_app.data.repository.implemetation.services

import br.sapiens.bellus_app.data.datasource.base.PutServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.PutServiceRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutServiceRepositoryImpl @Inject constructor(
    private val dataSource: PutServiceDataSource,
) : PutServiceRepository {

    override suspend fun put(schema: PutServiceSchemaEncapsulation): State<ServiceDTO> {
        return try {
            when (val response =
                dataSource.put(schema)) {
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