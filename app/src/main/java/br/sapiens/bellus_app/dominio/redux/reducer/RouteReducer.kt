package br.sapiens.bellus_app.dominio.redux.reducer


import br.sapiens.bellus_app.dominio.model.event.RouteEvent
import br.sapiens.bellus_app.dominio.model.state.RouteState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.stores.IStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RouteReducer @Inject constructor() {

    fun reduce(store: IStore<ApplicationState>): Flow<ApplicationState> {
        return store.stateFlow.map { currentState ->

            val newState: RouteState =
                when (val event: RouteEvent? = store.getLastAction() as? RouteEvent) {
                    is RouteEvent.UpdateRoute -> {
                        currentState.routeState.copy(
                            currentRoute = event.route
                        )
                    }

                    null -> TODO()
                }
            ApplicationState(routeState = newState)
        }
    }
}