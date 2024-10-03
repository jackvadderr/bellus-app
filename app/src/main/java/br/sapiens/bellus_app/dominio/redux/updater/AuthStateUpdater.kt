package br.sapiens.bellus_app.dominio.redux.updater

import android.content.Context
import br.sapiens.bellus_app.data.datastore.impl.AuthConfigManagerImpl.updateClientAuthState
import br.sapiens.bellus_app.dominio.model.event.AuthEvent
import br.sapiens.bellus_app.dominio.model.state.AuthState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthStateUpdater @Inject constructor() {

    fun update(
        event: AuthEvent,
        currentState: ApplicationState,
        context: Context
    ): ApplicationState {
        val newAuthState = when (event) {
            is AuthEvent.UserAuthenticatedAsClient -> {
                CoroutineScope(Dispatchers.IO).launch {
                    context.updateClientAuthState(true, event.user.id, event.tokenBearer)
                }
                AuthState.AuthenticatedAsClient(event.user, event.tokenBearer)
            }

            is AuthEvent.UserNotAuthenticated -> AuthState.Unauthenticated
            is AuthEvent.AuthenticationError -> AuthState.Error(event.exception)
            else -> currentState.authState
        }
        return currentState.copy(authState = newAuthState)
    }
}