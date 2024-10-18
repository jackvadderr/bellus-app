package br.sapiens.bellus_app.presentation.viewmodels

import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParceiroHomeViewModel @Inject constructor(

) : BaseViewModel<ParceiroHomeViewModel.ViewState, ParceiroHomeViewModel.ViewEvent>() {

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoadUser -> {}
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}