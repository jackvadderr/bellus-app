package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetUserRepositoryImpl @Inject constructor(
    private val getUserDataSource: GetUserDataSource,
) : GetUserRepository {

    override suspend fun getUserById(): State<UserDTO> {
        return try {
            when (val response = getUserDataSource.getUserById()) {
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