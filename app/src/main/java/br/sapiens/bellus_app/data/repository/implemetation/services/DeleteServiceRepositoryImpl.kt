package br.sapiens.bellus_app.data.repository.implemetation.services

import br.sapiens.bellus_app.data.datasource.base.DeleteServiceDataSource
import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.data.repository.base.DeleteServiceRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class DeleteServiceRepositoryImpl @Inject constructor(
    private val dataSource: DeleteServiceDataSource,
) : DeleteServiceRepository {

    override suspend fun delete(id: String): State<DeleteDTO> {
        return try {
            when (val response =
                dataSource.delete(id)) {
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