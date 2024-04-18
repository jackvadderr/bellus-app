package br.sapiens.bellus_app.data.repository.implemetation

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.repository.base.LoginRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

/**
 * Esta classe é uma implementação da interface LoginRepository.
 * Ela usa um LoginDataSource para autenticar o usuário.
 * A classe é injetada com a instância de LoginDataSource.
 *
 * @property loginDataSource A fonte de dados usada para autenticar o usuário.
 */
class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {

    /**
     * Esta função autentica um usuário com as credenciais de autenticação fornecidas.
     * Ela usa o LoginDataSource para autenticar o usuário.
     * Se ocorrer um erro durante a autenticação, ela retorna um State.Error com a exceção.
     *
     * @param authCredential As credenciais de autenticação do usuário.
     * @return Um objeto State que pode conter o objeto FirebaseUser ou uma exceção.
     */
    override suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser> {
        return try {
            // Tenta autenticar o usuário com as credenciais fornecidas chamando loginWithCredential do LoginDataSource.
            // Se a autenticação for bem-sucedida, retorna um State.Success com o objeto FirebaseUser.
            // Se ocorrer um erro durante a autenticação, retorna um State.Error com a exceção.
            when (val response = loginDataSource.loginWithCredential(authCredential)) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}