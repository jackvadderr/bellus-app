package br.sapiens.bellus_app.presentation.viewmodels

import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ParceiroModificarEstabelecimentoViewModel @Inject constructor(

) : BaseViewModel<ParceiroModificarEstabelecimentoViewModel.ViewState, ParceiroModificarEstabelecimentoViewModel.ViewEvent>() {
    override fun createInitialState(): ViewState {
        TODO("Not yet implemented")
    }

    override fun triggerEvent(event: ViewEvent) {
        TODO("Not yet implemented")
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}