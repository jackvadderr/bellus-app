package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

/**
 * Esta é uma interface funcional que representa um repositório para fazer login de um usuário.
 * Possui um único método abstrato `loginWithCredential`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface LoginRepository {

    /**
     * Esta é uma função suspensa que faz login de um usuário com a credencial de autenticação fornecida.
     * @param authCredential A credencial de autenticação a ser usada para fazer login do usuário.
     * @return Um objeto State que pode conter o objeto FirebaseUser ou uma exceção.
     */
    suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}