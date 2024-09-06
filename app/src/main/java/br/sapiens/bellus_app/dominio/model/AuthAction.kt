package br.sapiens.bellus_app.dominio.model

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

sealed class AuthAction {
    data class LoginAction(val authCredential: AuthCredential) : AuthAction()
    data class LoginSuccessAction(val user: FirebaseUser) : AuthAction()
    data class LoginFailureAction(val error: Throwable) : AuthAction()
    data object LogoutAction : AuthAction()
}
