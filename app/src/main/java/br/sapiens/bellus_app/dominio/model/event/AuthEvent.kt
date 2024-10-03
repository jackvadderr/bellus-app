package br.sapiens.bellus_app.dominio.model.event

import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.dominio.model.AuthUserClient
import br.sapiens.bellus_app.dominio.model.AuthUserProfessional

sealed class AuthEvent : IViewEvent {
    data class UserAuthenticatedAsClient(val user: AuthUserClient, val tokenBearer: String) :
        AuthEvent()

    data class UserAuthenticatedAsProfessional(
        val user: AuthUserProfessional,
        val tokenBearer: String
    ) : AuthEvent()

    data object UserNotAuthenticated : AuthEvent()
    data class AuthenticationError(val exception: Exception) : AuthEvent()
    data object TokenExpired : AuthEvent()
}
