package br.sapiens.bellus_app.dominio.redux.stores

import kotlinx.coroutines.flow.StateFlow

interface IStore<T> {
    val stateFlow: StateFlow<T>
    suspend fun dispatch(action: Any)
    suspend fun updateState(newState: T)
    fun getLastAction(): Any?
}