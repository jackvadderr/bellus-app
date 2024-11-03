package br.sapiens.bellus_app.dominio.redux.stores

import br.sapiens.bellus_app.dominio.model.event.PermissionEvent
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.PermissionReducer
import br.sapiens.bellus_app.dominio.redux.updater.PermissionStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionStore @Inject constructor(
    private val permissionReducer: PermissionReducer,
    private val permissionStateUpdater: PermissionStateUpdater,
    coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            permissionReducer.reducer(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    suspend fun dispatch(event: PermissionEvent) {
        val currentState: ApplicationState = store.stateFlow.value
        val newPermissionState = permissionStateUpdater.update(event, currentState)
        store.updateState(newPermissionState)
    }
}