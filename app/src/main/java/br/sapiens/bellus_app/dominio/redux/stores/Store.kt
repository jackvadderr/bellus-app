package br.sapiens.bellus_app.dominio.redux.stores

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) : IStore<T> {
    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow: StateFlow<T> = _stateFlow

    private var lastAction: Any? = null
    private val mutex = Mutex() // Inicializa o Mutex para garantir segurança na concorrência

    // dispatch agora é uma função suspensa
    override suspend fun dispatch(action: Any) {
        mutex.withLock {
            lastAction = action
        }
    }

    override suspend fun updateState(newState: T) {
        mutex.withLock {
            _stateFlow.value = newState
        }
    }

    override fun getLastAction(): Any? = lastAction
}


