package br.sapiens.bellus_app.data.repository.implemetation.services

import br.sapiens.bellus_app.data.datasource.base.PostServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.PostServiceRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostServiceSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostServiceRepositoryImpl @Inject constructor(
    private val dataSource: PostServiceDataSource,
) : PostServiceRepository {

    override suspend fun post(schema: PostServiceSchema): State<ServiceDTO> {
        return try {
            when (val response =
                dataSource.post(schema)) {
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