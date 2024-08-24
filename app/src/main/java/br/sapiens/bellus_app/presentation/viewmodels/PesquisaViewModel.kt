package br.sapiens.bellus_app.presentation.viewmodels

import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class PesquisaViewModel @Inject constructor(

): BaseViewModel<PesquisaViewModel.ViewState, PesquisaViewModel.ViewEvent>() {
    override fun createInitialState(): ViewState {
        // Retornar um estado inicial válido
        return ViewState()
    }

    override fun triggerEvent(event: ViewEvent) {
        // Implementar a lógica para lidar com os eventos
        when (event) {
            is ViewEvent.SomeEvent -> {
                // Lógica para lidar com SomeEvent
            }
            // Adicionar outros eventos conforme necessário
        }
    }

    class ViewState : IViewState {
        // Definir propriedades do estado
    }

    sealed class ViewEvent : IViewEvent {
        data object SomeEvent : ViewEvent()
        // Definir outros eventos conforme necessário
    }
}
