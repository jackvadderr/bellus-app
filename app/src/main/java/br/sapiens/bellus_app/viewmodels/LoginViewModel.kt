package br.sapiens.bellus_app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.ViewEventImpl
import br.sapiens.bellus_app.base.ViewStateImpl
import br.sapiens.bellus_app.domain.useCase.LoginUseCase
import com.google.firebase.auth.AuthCredential
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent>() {


    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onUsernameChanged(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    fun onLoginClicked(navigateToHome: () -> Unit) {
        // Aqui você pode adicionar a lógica para autenticar o usuário
        // Se a autenticação for bem-sucedida, você pode chamar navigateToHome()
    }


    override val uiState: StateFlow<ViewState> = MutableStateFlow(createInitialState())

    fun loginWithCredential(authCredential: AuthCredential) {
        setState { state.copy(isLoading = true) }
        viewModelScope.launch {
            when (loginUseCase.execute(LoginUseCase.Input(authCredential = authCredential))) {
                is State.Success<*> -> {
                    triggerEvent(ViewEvent.SetState(EstadoAutenticacao.AUTENTICADO))
                }
                is State.Error -> {
                    triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
                }
            }
        }


    }


    override fun createInitialState(): ViewState = ViewState()

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            when (event) {
                is ViewEvent.SetState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = event.state
                        )
                    }
                }
                is ViewEvent.SetLoading -> {
                    setState {
                        state.copy(
                            isLoading = event.state
                        )
                    }
                }
            }
        }
    }


    sealed class ViewEvent : ViewEventImpl {
        class SetLoading(val state: Boolean) : ViewEvent()
        class SetState(val state: EstadoAutenticacao) : ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val loginState: EstadoAutenticacao? = null,
    ) : ViewStateImpl
}


