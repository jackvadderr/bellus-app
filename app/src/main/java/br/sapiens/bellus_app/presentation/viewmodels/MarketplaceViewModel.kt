package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.usecase.GetUserUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when(event) {
            is ViewEvent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            setState { ViewState.Loading }
            when (val result = getUserUseCase.invoke(null)) {
                is State.Success -> {
                    Log.d("MarketplaceViewModel", "User data: ${result.data}")
                    setState { ViewState.UserLoaded }
                }
                is State.Error -> setState { ViewState.Loading }
            }
        }
    }

    sealed class ViewState: IViewState {
        data object Loading: ViewState()
        data object UserLoaded : ViewState()
    }

    sealed class ViewEvent: IViewEvent {
        object LoadUser: ViewEvent()
    }

}