package br.sapiens.bellus_app.dominio.usecase.login

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.AuthDTO
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential

class LoginUseCase(
    private val loginRepository: LoginRepository,
) : UseCase<LoginUseCase.Input?, AuthDTO>() {

    override suspend fun invoke(input: Input?): State<AuthDTO> {
        return try {
            input?.authCredential?.let { credential ->
                when (val response = loginRepository.loginWithCredential(credential)) {
                    is State.Success<*> -> {
                        val firebaseUser: AuthDTO = response.data as AuthDTO
                        State.Success(firebaseUser)
                    }

                    is State.Error -> response
                }
            } ?: State.Error(Exception("Input or AuthCredential is null"))
        } catch (e: Exception) {
            State.Error(e)
        }
    }

    data class Input(
        val authCredential: AuthCredential,
    ) : Inputs
}