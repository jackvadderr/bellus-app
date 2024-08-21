package br.sapiens.bellus_app.dominio

import br.sapiens.bellus_app.dominio.model.AuthEvent
import br.sapiens.bellus_app.dominio.model.AuthState
import br.sapiens.bellus_app.dominio.sdk.AuthService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


object GlobalAuthState {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val authService = AuthService(firebaseAuth)

    private val _authState = MutableStateFlow(
        if (firebaseAuth.currentUser != null) {
            AuthState.Authenticated(
                userId = firebaseAuth.currentUser?.uid.orEmpty(),
                email = firebaseAuth.currentUser?.email.orEmpty(),
                isEmailVerified = firebaseAuth.currentUser?.isEmailVerified ?: false,
                providerId = firebaseAuth.currentUser?.providerId.orEmpty()
            )
        } else {
            AuthState.Unauthenticated
        }
    )
    val authState: StateFlow<AuthState> = _authState

    init {
        observeAuthEvents()
    }

    private fun observeAuthEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            authService.authEvents.collect { event ->
                when (event) {
                    is AuthEvent.UserAuthenticated -> updateStateToAuthenticated()
                    is AuthEvent.UserNotAuthenticated -> updateStateToUnauthenticated()
                    is AuthEvent.AuthenticationError -> handleAuthenticationError(event.exception)
                    else -> {}
                }
            }
        }
    }

    private fun updateStateToAuthenticated() {
        firebaseAuth.currentUser?.let { user ->
            _authState.value = AuthState.Authenticated(
                userId = user.uid,
                email = user.email.orEmpty(),
                isEmailVerified = user.isEmailVerified,
                providerId = user.providerId.orEmpty()
            )
        } ?: run {
            _authState.value = AuthState.Unauthenticated
        }
    }

    private fun updateStateToUnauthenticated() {
        _authState.value = AuthState.Unauthenticated
    }

    private fun handleAuthenticationError(exception: Exception) {
        // Trata erros de autenticação conforme necessário
    }

    suspend fun refreshUserToken(onSuccess: () -> Unit, onFailure: () -> Unit) {
        authService.refreshUserToken(onSuccess, onFailure)
    }

    fun signOut() {
        authService.signOut()
    }

    fun getCurrentUserId(): String? {
        return when (_authState.value) {
            is AuthState.Authenticated -> (_authState.value as AuthState.Authenticated).userId
            else -> null
        }
    }
}