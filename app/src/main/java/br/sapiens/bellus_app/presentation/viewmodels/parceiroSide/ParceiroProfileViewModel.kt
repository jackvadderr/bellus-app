package br.sapiens.bellus_app.presentation.viewmodels.parceiroSide

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datastore.impl.UserDataConfigManagerImpl.userData
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.ClientInfo
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParceiroProfileViewModel @Inject constructor(
    userProfileStore: UserProfileStore,
    private val context: Context
) : BaseViewModel<ParceiroProfileViewModel.ViewState, ParceiroProfileViewModel.ViewEvent>() {

    val userStore = userProfileStore

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoadUser -> {
                Log.d("ParceiroProfileViewModel", "LoadUser event triggered")
            }
        }
    }

    fun getLocalUser() {
        viewModelScope.launch {
            Log.d("ParceiroProfileViewModel", "getLocalUser called")
            context.userData.collectLatest { userData ->
                Log.d("ParceiroProfileViewModel", "Collected userData: $userData")
                userData.client?.let {
                    Log.d("ParceiroProfileViewModel", "Client data found: $it")
                    userStore.dispatch(
                        UserProfileEvent.SetClientProfile(
                            ClientInfo(
                                id = it.id,
                                name = it.name,
                                email = it.email,
                                phone = it.phone
                            )
                        )
                    )
                } ?: run {
                    Log.d("ParceiroProfileViewModel", "No client data found")
                }
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}