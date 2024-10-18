package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // Essa é a viewmodel para trocar de perfil, acho que não vai dá certo
class UserProfileViewModel @Inject constructor(
    private val userProfileStore: UserProfileStore
) : BaseViewModel<UserProfileViewModel.ViewState, UserProfileViewModel.ViewEvent>() {


    fun dispatch(event: UserProfileEvent) {
        viewModelScope.launch {
            userProfileStore.dispatch(event)
        }
        setState {
            ViewState.CurrentProfileType(userProfileStore.getActiveProfileType())
        }
    }

    fun getActiveProfileType(): UserProfileType {
        return userProfileStore.getActiveProfileType()
    }

    override fun createInitialState(): ViewState {
        TODO("Not yet implemented")
    }

    override fun triggerEvent(event: ViewEvent) {
        TODO("Not yet implemented")
    }


    sealed class ViewState : IViewState {
        data class CurrentProfileType(val currentProfileType: UserProfileType) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
    }


}