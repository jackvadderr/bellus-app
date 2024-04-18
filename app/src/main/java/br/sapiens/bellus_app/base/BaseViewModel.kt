package br.sapiens.bellus_app.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * BaseViewModel é uma classe abstrata que fornece uma implementação base para um ViewModel.
 * Ela usa Kotlin Coroutines e StateFlow para gerenciar o estado e os eventos.
 *
 * @param State O tipo do estado. Deve implementar IViewState.
 * @param Event O tipo do evento. Deve implementar IViewEvent.
 */
abstract class BaseViewModel<State : IViewState, Event : IViewEvent> : ViewModel() {

    // O estado inicial do ViewModel, criado de forma preguiçosa.
    private val initialState: State by lazy { createInitialState() }

    /**
     * Função abstrata para criar o estado inicial.
     * Isso deve ser implementado por subclasses.
     *
     * @return O estado inicial.
     */
    abstract fun createInitialState(): State

    // O estado atual do ViewModel.
    val state: State get() = uiState.value

    /**
     * Função abstrata para acionar um evento.
     * Isso deve ser implementado por subclasses.
     *
     * @param event O evento a ser acionado.
     */
    abstract fun triggerEvent(event: Event)

    // O MutableStateFlow para gerenciar o estado.
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    // O StateFlow para expor o estado aos observadores.
    open val uiState: StateFlow<State> = _uiState

    // O MutableSharedFlow para gerenciar os eventos.
    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
    // O SharedFlow para expor os eventos aos observadores.
    val uiEvent = _uiEvent.asSharedFlow()

    /**
     * Função para definir o estado.
     * Isso usa uma função redutora para criar o novo estado.
     *
     * @param reduce A função redutora.
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = state.reduce()
        _uiState.value = newState
        Log.d("BaseViewModel", "setState: $newState")
    }

    /**
     * Função para definir um evento.
     * Isso emite o evento para o SharedFlow.
     *
     * @param event O evento a ser definido.
     */
    protected fun setEvent(event: Event) {
        viewModelScope.launch { _uiEvent.emit(event) }
    }

    /**
     * Função para chamar um Flow e lidar com sua conclusão.
     * Isso captura quaisquer exceções e invoca o manipulador de conclusão quando o Flow é concluído.
     *
     * @param callFlow O Flow a ser chamado.
     * @param completionHandler O manipulador de conclusão.
     * @param errorHandler O manipulador de erro.
     */
    protected fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {},
        errorHandler: (exception: Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            callFlow
                .catch { exception -> errorHandler.invoke(exception) }
                .collect { result -> completionHandler.invoke(result) }
        }
    }
}