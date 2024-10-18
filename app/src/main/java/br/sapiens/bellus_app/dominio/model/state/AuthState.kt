package br.sapiens.bellus_app.dominio.model.state

import br.sapiens.bellus_app.dominio.model.AuthUser

/**
 * Representa os possíveis estados de autenticação de um usuário na aplicação.
 */
sealed class AuthState {
    data class AuthenticatedAsClient(val user: AuthUser, val token: String) : AuthState()
    data class AuthenticatedAsProfessional(val user: AuthUser, val token: String) :
        AuthState()

    data object Unauthenticated : AuthState()
    data class Error(val error: Throwable) : AuthState()

    //    fun getEmail(): String? = (this as? Authenticated)?.user?.email
    fun getUserId(): String? = (this as? AuthenticatedAsClient)?.user?.id

//    fun isEmailVerified(): Boolean = (this as? Authenticated)?.user?.isEmailVerified ?: false

//    fun getProviderId(): String = (this as? Authenticated)?.user?.providerId ?: ""
}
