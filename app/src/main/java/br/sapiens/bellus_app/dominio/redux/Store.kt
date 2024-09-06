package br.sapiens.bellus_app.dominio.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.sync.Mutex
//import kotlinx.coroutines.sync.withLock

interface IStore<T> {
    val stateFlow: StateFlow<T>
    fun dispatch(action: Any)
    fun updateState(newState: T)
    fun getLastAction(): Any?
}


class Store<T>(initialState: T) : IStore<T> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow: StateFlow<T> = _stateFlow

    private var lastAction: Any? = null

    override fun dispatch(action: Any) {
        lastAction = action
    }

    override fun updateState(newState: T) {
        _stateFlow.value = newState
    }

    override fun getLastAction(): Any? = lastAction
}
