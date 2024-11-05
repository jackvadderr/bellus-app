package br.sapiens.bellus_app.data.repository.implemetation.user

import br.sapiens.bellus_app.data.datasource.base.GetUserByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.data.repository.base.GetUserByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetUserByIdRepositoryImpl @Inject constructor(
    private val dataSource: GetUserByIdDataSource,
) : GetUserByIdRepository {

    override suspend fun getById(id: String): State<UserDTO> {
        return try {
            when (val response = dataSource.getById(id)) {
                is State.Success -> {
                    val user = response.data
                    State.Success(user)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}