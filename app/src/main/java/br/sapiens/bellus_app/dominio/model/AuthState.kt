package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.base.IViewState

/**
 * Representa os possíveis estados de autenticação de um usuário na aplicação.
 */
sealed class AuthState: IViewState {
    /**
     * Estado quando o usuário está autenticado.
     *
     * @property userId O identificador único do usuário.
     * @property email O endereço de e-mail do usuário.
     * @property isEmailVerified Indica se o e-mail do usuário foi verificado.
     * @property providerId O identificador do provedor de autenticação.
     */
    data class Authenticated(
        val userId: String,
        val email: String,
        val isEmailVerified: Boolean,
        val providerId: String
    ) : AuthState()

    /**
     * Estado quando nenhum usuário está autenticado.
     */
    object Unauthenticated : AuthState()
}
