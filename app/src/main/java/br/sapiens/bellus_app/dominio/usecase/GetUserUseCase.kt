package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.model.User
import br.sapiens.bellus_app.dominio.GlobalAuthState
import br.sapiens.bellus_app.dominio.model.AuthState
import br.sapiens.bellus_app.utils.CommonException
import br.sapiens.bellus_app.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val getUserRepository: GetUserRepository,
) : UseCase<Nothing, User>() {

    public override suspend fun invoke(input: Nothing?): State<User> {
        return try {
            GlobalAuthState.getCurrentUserId()?.let {
                when (val response = getUserRepository.getUserById(it)) {
                    is State.Success -> response
                    is State.Error -> response
                }
            } ?: run {
                State.Error(CommonException())
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}