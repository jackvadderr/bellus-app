package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.base.IViewEvent
import com.google.firebase.auth.FirebaseUser

sealed class AuthEvent: IViewEvent {
    data class UserAuthenticated(val user: AuthUser, val tokenBearer: String) : AuthEvent()
    data object UserNotAuthenticated : AuthEvent()
    data class AuthenticationError(val exception: Exception) : AuthEvent()
    data object TokenExpired: AuthEvent()
}
