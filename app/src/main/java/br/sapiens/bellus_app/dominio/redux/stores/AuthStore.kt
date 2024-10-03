package br.sapiens.bellus_app.dominio.redux.stores

import android.content.Context
import android.util.Log
import br.sapiens.bellus_app.data.datastore.impl.AuthConfigManagerImpl.authConfig
import br.sapiens.bellus_app.dominio.model.AuthUserClient
import br.sapiens.bellus_app.dominio.model.event.AuthEvent
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.AuthReducer
import br.sapiens.bellus_app.dominio.redux.updater.AuthStateUpdater
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
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
    private val provider: KtorClientProvider,
    private val coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            Log.d("AuthStore", "Initializing AuthStore")
            checkClientTokenInDataStore()

            authReducer.reduce(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    suspend fun dispatch(event: AuthEvent) {
        val currentState = store.stateFlow.value
        val newState = authStateUpdater.update(event, currentState, context)
        store.updateState(newState)
        store.dispatch(event)
    }

    private fun checkClientTokenInDataStore() {
        coroutineScope.launch(Dispatchers.IO) {
            context.authConfig.collectLatest { authConfig ->
                if (authConfig.isAuthenticatedClient) {
                    val authUserClient = AuthUserClient(authConfig.userIdClient)
                    val tokenBearer = authConfig.tokenBearerClient
                    provider.setBearerTokenPrimary(tokenBearer)
                    dispatch(AuthEvent.UserAuthenticatedAsClient(authUserClient, tokenBearer))
                } else {
                    dispatch(AuthEvent.UserNotAuthenticated)
                }
            }
        }
    }

    private fun checkProfessionalTokenInDataStore() {
        coroutineScope.launch(Dispatchers.IO) {
            context.authConfig.collectLatest { authConfig ->
                if (authConfig.isAuthenticatedClient) {
                    val authUserClient = AuthUserClient(authConfig.userIdClient)
                    val tokenBearer = authConfig.tokenBearerClient
                    provider.setBearerTokenPrimary(tokenBearer)
                    dispatch(AuthEvent.UserAuthenticatedAsClient(authUserClient, tokenBearer))
                } else {
                    dispatch(AuthEvent.UserNotAuthenticated)
                }
            }
        }
    }
}