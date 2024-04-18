package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser


/**
 * Esta é uma interface funcional que contém uma única função suspensa para fazer login com uma credencial.
 * É usada como uma fonte de dados para realizar operações de login.
 */
fun interface LoginDataSource {

    /**
     * Esta é uma função suspensa que faz login com uma credencial fornecida.
     * Retorna um objeto State que contém o objeto FirebaseUser.
     * O objeto State representa o estado da operação de login.
     *
     * @param authCredential A credencial usada para login.
     * @return Um objeto State contendo o objeto FirebaseUser.
     */
    suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}