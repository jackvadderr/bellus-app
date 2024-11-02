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
    private val storeConfig: AuthStore,
    private val storeUser: UserProfileStore,
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

            val userType: UserProfileType =
                storeUser.store.stateFlow.value.userProfileState.currentProfileType
            Log.d("SplashViewModel", "User type: $userType")

            val userId: String? = storeConfig.store.stateFlow.value.authState.getUserId()
            Log.d("SplashViewModel", "User ID: $userId")
            if (!userId.isNullOrEmpty()) {
                Log.d("SplashViewModel", "User ID is not empty")
                setState {
                    ViewState.AuthState(EstadoAutenticacao.AUTENTICADO)
                }
                Log.d("SplashViewModel", "DEBUG 2")
                if (userType == UserProfileType.CLIENT) {
                    Log.d("SplashViewModel", "User Type: CLIENT")
                    val clientInfo = ClientInfo(
                        id = userId,
                    )
                    storeUser.dispatch(
                        UserProfileEvent.SetClientProfile(
                            clientInfo
                        )
                    )
                    setState {
                        ViewState.ProfileType(
                            userType
                        )
                    }
                } else if (userType == UserProfileType.PROFESSIONAL) {
                    Log.d("SplashViewModel", "User Type: PROFESSIONAL")
                    setState {
                        ViewState.AuthState(EstadoAutenticacao.AUTENTICADO)
                    }
//                    val professionalInfo = ProfessionalInfo(
//                        id = userId,
//                    )
//                    storeUser.dispatch(
//                        UserProfileEvent.SetProfessionalProfile(
//                            professionalInfo
//                        )
//                    )
                    setState {
                        ViewState.ProfileType(
                            userType
                        )
                    }
                }
            } else {
                Log.d("SplashViewModel", "User ID is empty")
                setState {
                    ViewState.AuthState(EstadoAutenticacao.NAO_AUTENTICADO)
                }
            }

        }
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