package br.sapiens.bellus_app.dominio.redux.stores

import android.content.Context
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.ClientInfo
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
    private val context: Context,
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
        val newUserProfileState = userProfileStateUpdater.update(event, currentState, context)
        store.updateState(newUserProfileState)
    }


    fun getActiveProfileType(): UserProfileType {
        return store.stateFlow.value.userProfileState.currentProfileType
    }

    fun getEstablishmentId(): String? {
        return store.stateFlow.value.userProfileState.professionalInfo?.establishmentId
    }

    fun getCurrentClientInfo(): ClientInfo? {
        return store.stateFlow.value.userProfileState.clientInfo
    }

    fun getCurrentClientInfoName(): String? {
        return store.stateFlow.value.userProfileState.clientInfo?.name
    }

//    fun hasUserClient(): Boolean {
//        return store.stateFlow.value.userProfileState.clientInfo != null
//    }

    fun getCurrentUserId(): String? {
        return store.stateFlow.value.userProfileState.clientInfo?.id
    }

    suspend fun setNameClientInfo(name: String) {
        val currentState: ApplicationState = store.stateFlow.value
        val newClientInfo = currentState.userProfileState.clientInfo?.copy(name = name)
        val newUserProfileState = currentState.userProfileState.copy(clientInfo = newClientInfo)
        store.updateState(currentState.copy(userProfileState = newUserProfileState))
    }
}