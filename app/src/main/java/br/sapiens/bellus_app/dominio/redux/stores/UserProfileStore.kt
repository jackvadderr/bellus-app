package br.sapiens.bellus_app.dominio.redux.stores

import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.UserProfileReducer
import br.sapiens.bellus_app.dominio.redux.updater.UserProfileStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileStore @Inject constructor(
    private val userProfileReducer: UserProfileReducer,
    private val userProfileStateUpdater: UserProfileStateUpdater,
    coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            userProfileReducer.reduce(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    suspend fun dispatch(event: UserProfileEvent) {
        val currentState: ApplicationState = store.stateFlow.value
        val newUserProfileState = userProfileStateUpdater.update(event, currentState)
        store.updateState(newUserProfileState)
    }


    fun getActiveProfileType(): UserProfileType {
        return store.stateFlow.value.userProfileState.currentProfileType
    }

    fun getClientInfo(): String? {
        return store.stateFlow.value.userProfileState.clientInfo?.name
    }

    fun hasUserClient(): Boolean {
        return store.stateFlow.value.userProfileState.clientInfo != null
    }

    fun getCurrentUserId(): String? {
        return store.stateFlow.value.userProfileState.clientInfo?.id
    }
}