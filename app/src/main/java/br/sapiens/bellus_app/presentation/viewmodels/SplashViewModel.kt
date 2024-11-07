package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.ClientInfo
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.stores.AuthStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authStore: AuthStore,
    private val userStore: UserProfileStore,
) : BaseViewModel<SplashViewModel.ViewState, SplashViewModel.ViewEvent>() {
    private var splashShowFlow = MutableStateFlow(true)
    var isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
            splashShowFlow.value = false
        }
        Log.d("SplashViewModel", "DEBUG 0")
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("SplashViewModel", "Creating initial state: Loading")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            Log.d("SplashViewModel", "Triggering event: $event")
            when (event) {
                ViewEvent.Loading -> {
                    Log.d("SplashViewModel", "Event: Loading")
                    checkUser()
                }
            }
        }
    }

    private fun checkUser() {
        viewModelScope.launch {
            Log.d("SplashViewModel", "Checking user")
            delay(2000)

            val userType = userStore.store.stateFlow.value.userProfileState.currentProfileType
            val userId = authStore.store.stateFlow.value.authState.getUserId()
            val token = authStore.store.stateFlow.value.authState.getTokenSession()

            if (userId.isNullOrEmpty() || token.isNullOrEmpty()) {
                handleUnauthenticatedUser()
            } else {
                handleAuthenticatedUser(userId, userType)
            }
        }
    }

    private fun handleUnauthenticatedUser() {
        Log.d("SplashViewModel", "User ID is empty")
        setState {
            ViewState.AuthState(EstadoAutenticacao.NAO_AUTENTICADO)
        }
    }

    private suspend fun handleAuthenticatedUser(userId: String, userType: UserProfileType) {
        Log.d("SplashViewModel", "User ID is not empty")
        setState {
            ViewState.AuthState(EstadoAutenticacao.AUTENTICADO)
        }
        Log.d("SplashViewModel", "DEBUG 2")

        when (userType) {
            UserProfileType.CLIENT -> handleClientUser(userId)
            UserProfileType.PROFESSIONAL -> handleProfessionalUser(userId)
        }
    }

    private suspend fun handleClientUser(userId: String) {
        Log.d("SplashViewModel", "User Type: CLIENT")
        val clientInfo = ClientInfo(id = userId)
        userStore.dispatch(UserProfileEvent.SetClientProfile(clientInfo))
        setState {
            ViewState.ProfileType(UserProfileType.CLIENT)
        }
    }

    private suspend fun handleProfessionalUser(userId: String) {
        Log.d("SplashViewModel", "User Type: PROFESSIONAL")
        setState {
            ViewState.AuthState(EstadoAutenticacao.AUTENTICADO)
        }
        // Uncomment and implement if needed
        // val professionalInfo = ProfessionalInfo(id = userId)
        // storeUser.dispatch(UserProfileEvent.SetProfessionalProfile(professionalInfo))
        setState {
            ViewState.ProfileType(UserProfileType.PROFESSIONAL)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Adicione aqui o código para liberar recursos ou cancelar operações
        Log.d("SplashViewModel", "ViewModel is being cleared")
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class AuthState(val authState: EstadoAutenticacao) : ViewState()
        data class ProfileType(val profileType: UserProfileType) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
    }
}

//sealed class ViewState : IViewState {
//    data object Loading : ViewState()
//    data class CreateAppointment(val post: AppointmentDTO) : ViewState()
//}
//
//sealed class ViewEvent : IViewEvent {
//    data class CreateAppointment(val servicePost: ServicePost) : ViewEvent()
//}