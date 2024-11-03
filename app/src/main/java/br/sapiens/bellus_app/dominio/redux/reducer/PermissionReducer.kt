package br.sapiens.bellus_app.dominio.redux.reducer

import br.sapiens.bellus_app.dominio.model.event.PermissionEvent
import br.sapiens.bellus_app.dominio.model.state.PermissionState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.stores.IStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PermissionReducer @Inject constructor() {

    fun reducer(store: IStore<ApplicationState>): Flow<ApplicationState> {
        return store.stateFlow.map { currentState ->
            val newState: PermissionState =
                when (val event: PermissionEvent? = store.getLastAction() as? PermissionEvent) {
                    is PermissionEvent.GalleryPermissionGranted -> {
                        currentState.permissionState.copy(
                            isGalleryPermissionGranted = event.granted
                        )
                    }

                    else -> currentState.permissionState
                }
            currentState.copy(permissionState = newState)
        }
    }
}