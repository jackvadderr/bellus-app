package br.sapiens.bellus_app.dominio.redux.stores

import br.sapiens.bellus_app.dominio.model.event.RouteEvent
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.RouteReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutaStore @Inject constructor(
    private val routeReducer: RouteReducer,
    coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            routeReducer.reduce(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    suspend fun dispatch(event: RouteEvent) {
        val currentState: ApplicationState = store.stateFlow.value
        routeReducer.reduce(store).collectLatest { newState ->
            store.updateState(currentState.copy(routeState = newState.routeState))
        }
    }

    fun getCurrentRoute(): String? {
        return store.stateFlow.value.routeState.currentRoute
    }
}