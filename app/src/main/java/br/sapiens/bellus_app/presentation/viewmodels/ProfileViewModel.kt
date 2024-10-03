package br.sapiens.bellus_app.presentation.viewmodels

import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.ViewEvent>() {

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoadUser -> TODO()
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val availableEstablishments: List<AvailableEstablishment>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}