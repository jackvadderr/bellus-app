package br.sapiens.bellus_app.domain.useCase

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.repository.LoginRepositoryImpl
import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepositoryImpl,
) : UseCase<LoginUseCase.Input?, FirebaseUser>() {

    override suspend fun invoke(input: Input?): State<FirebaseUser> {
        return try {
            when (val response = loginRepository.loginWithCredential(input!!.authCredential)) {
                is State.Success<*> -> response as State.Success<FirebaseUser>
                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }

    data class Input(
        val authCredential: AuthCredential,
    ) : Inputs
}

