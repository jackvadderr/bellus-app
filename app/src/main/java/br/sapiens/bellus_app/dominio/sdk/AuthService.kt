package br.sapiens.bellus_app.dominio.sdk

import br.sapiens.bellus_app.dominio.model.AuthEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthService(private val firebaseAuth: FirebaseAuth) {

    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents: SharedFlow<AuthEvent> = _authEvents.asSharedFlow()

    fun signOut() {
        firebaseAuth.signOut()
        _authEvents.tryEmit(AuthEvent.UserNotAuthenticated)
    }

    suspend fun refreshUserToken(onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                _authEvents.tryEmit(AuthEvent.UserAuthenticated)
            } else {
                onFailure()
                _authEvents.tryEmit(AuthEvent.AuthenticationError(task.exception ?: Exception("Unknown error")))
            }
        }
    }
}

