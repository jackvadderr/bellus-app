package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val getUserRepository: GetUserRepository
) : UseCase<Nothing, UserDTO>() {

    override suspend fun invoke(input: Nothing?): State<UserDTO> {
        return try {
            val userId = getUserRepository.getUserById()
            userId.let {
                when (val response = getUserRepository.getUserById()) {
                    is State.Success -> response
                    is State.Error -> response
                }
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}