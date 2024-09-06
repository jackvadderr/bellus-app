package br.sapiens.bellus_app.dominio.redux

import android.content.Context
import br.sapiens.bellus_app.data.datastore.impl.AuthConfigManagerImpl.authConfig
import br.sapiens.bellus_app.dominio.model.AuthEvent
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.redux.reducer.AuthReducer
import br.sapiens.bellus_app.dominio.redux.updater.AuthStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStore @Inject constructor(
    private val authStateUpdater: AuthStateUpdater,
    private val authReducer: AuthReducer,
    private val context: Context,
    private val coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            checkTokenInDataStore()

            authReducer.reduce(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    fun dispatch(event: AuthEvent) {
        val currentState = store.stateFlow.value
        val newState = authStateUpdater.update(event, currentState, context)
        store.updateState(newState)
        store.dispatch(event)
    }

    private fun checkTokenInDataStore() {
        coroutineScope.launch(Dispatchers.IO) {
            context.authConfig.collectLatest { authConfig ->
                if (authConfig.isAuthenticated) {
                    val authUser = AuthUser(authConfig.userId)
                    val tokenBearer = authConfig.tokenBearer
                    dispatch(AuthEvent.UserAuthenticated(authUser, tokenBearer))
                } else {
                    dispatch(AuthEvent.UserNotAuthenticated)
                }
            }
        }
    }
}