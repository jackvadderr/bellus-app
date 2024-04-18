package br.sapiens.bellus_app.data.datasource.implemetation

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.utils.State
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementação da interface LoginDataSource.
 *
 * Esta classe fornece a funcionalidade para fazer login de um usuário com uma credencial de autenticação fornecida.
 * Utiliza a Autenticação Firebase para o processo de login.
 */
class LoginDataSourceImpl @Inject constructor() : LoginDataSource {

    /**
     * Faz login de um usuário com a credencial de autenticação fornecida.
     *
     * Esta função utiliza a Autenticação Firebase para fazer login do usuário. Retorna um objeto State
     * que pode ser um estado de Sucesso com o FirebaseUser logado, ou um estado de Erro com
     * a exceção que ocorreu durante o processo de login.
     *
     * @param authCredential A credencial de autenticação para fazer login do usuário.
     * @return Um objeto State representando o resultado do processo de login.
     */
    override suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser> {
        return try {
            // Executa a operação de login com a credencial de autenticação fornecida.
            val firebaseAuthInstance = FirebaseAuth.getInstance()
            firebaseAuthInstance.signInWithCredential(authCredential).await()
            State.Success(firebaseAuthInstance.currentUser!!)
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}