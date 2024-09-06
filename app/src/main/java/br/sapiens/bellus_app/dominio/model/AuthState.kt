package br.sapiens.bellus_app.dominio.model

/**
 * Representa os possíveis estados de autenticação de um usuário na aplicação.
 */
sealed class AuthState {
    data class Authenticated(val user: AuthUser, val token: String) : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val error: Throwable) : AuthState()

//    fun getEmail(): String? = (this as? Authenticated)?.user?.email
    fun getUserId(): String? = (this as? Authenticated)?.user?.id

//    fun isEmailVerified(): Boolean = (this as? Authenticated)?.user?.isEmailVerified ?: false

//    fun getProviderId(): String = (this as? Authenticated)?.user?.providerId ?: ""
}
