package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import br.sapiens.bellus_app.data.repository.base.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository,
) : UseCase<LoginUseCase.Input?, FirebaseUser>() {

    override suspend fun invoke(input: Input?): State<FirebaseUser> {
        return try {
            when (val response = loginRepository.loginWithCredential(input!!.authCredential)) {
                is State.Success<*> -> State.Success(response.data as FirebaseUser)
                is State.Error -> response
                else -> State.Error(Exception("Error"))
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }

    data class Input(
        val authCredential: AuthCredential,
    ) : Inputs
}