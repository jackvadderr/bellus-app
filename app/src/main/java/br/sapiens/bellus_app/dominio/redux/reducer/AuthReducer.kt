package br.sapiens.bellus_app.dominio.redux.reducer

import br.sapiens.bellus_app.dominio.model.AuthEvent
import br.sapiens.bellus_app.dominio.model.AuthState
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.FirebaseUserProvider
import br.sapiens.bellus_app.dominio.redux.stores.IStore
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthReducer @Inject constructor(
    private val firebaseUserProvider: FirebaseUserProvider
) {

    fun reduce(store: IStore<ApplicationState>): Flow<ApplicationState> {
        return store.stateFlow.map { currentState ->

            val newState: AuthState = when (val event = store.getLastAction() as? AuthEvent) {
                is AuthEvent.UserAuthenticated -> {
                    val user: FirebaseUser? = firebaseUserProvider.getCurrentUser()
                    if (user != null) {
                        AuthState.Authenticated(
                            AuthUser(user.uid),
                            event.tokenBearer
                        )
                    } else {
                        AuthState.Unauthenticated
                    }
                }

                is AuthEvent.UserNotAuthenticated -> AuthState.Unauthenticated
                is AuthEvent.AuthenticationError -> AuthState.Error(event.exception)
                else -> currentState.authState
            }

            ApplicationState(authState = newState)
        }
    }
}