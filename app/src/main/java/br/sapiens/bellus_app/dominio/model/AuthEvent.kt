package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.base.IViewEvent

sealed class AuthEvent: IViewEvent {
    data object UserAuthenticated : AuthEvent()
    data object UserNotAuthenticated : AuthEvent()
    data class AuthenticationError(val exception: Exception) : AuthEvent()
}
