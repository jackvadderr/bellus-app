package br.sapiens.bellus_app.dominio.usecase.user

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.data.repository.base.GetUserByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val getUserRepository: GetUserByIdRepository
) : UseCase<String, UserDTO>() {

    public override suspend fun invoke(input: String?): State<UserDTO> {
        return try {
//            val userId = getUserRepository.getById(input!!)
//            userId.let {
            when (val response = getUserRepository.getById(input!!)) {
                is State.Success -> response
                is State.Error -> response
            }
//            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}