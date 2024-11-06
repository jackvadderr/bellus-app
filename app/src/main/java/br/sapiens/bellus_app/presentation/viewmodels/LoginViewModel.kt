package br.sapiens.bellus_app.presentation.viewmodels


import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AuthDTO
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.model.event.AuthEvent
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.ClientInfo
import br.sapiens.bellus_app.dominio.redux.stores.AuthStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.usecase.login.LoginUseCase
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val storeConfig: AuthStore,
    private val storeUser: UserProfileStore,
    context: Context
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent>() {

    val context = context

    fun loginWithCredential(authCredential: AuthCredential, callback: LoginCallback) {
        Log.d("LoginViewModel", "Starting loginWithCredential")
        setState { state.copy(isLoading = true) }
        viewModelScope.launch {
            Log.d("LoginViewModel", "Launching coroutine for login")
            when (val result: State<AuthDTO> =
                loginUseCase.execute(LoginUseCase.Input(authCredential = authCredential))) {
                is State.Success -> {
                    Log.d("LoginViewModel", "Login successful: ${result.data}")
                    val authUser = AuthUser(result.data.id ?: "")
                    storeConfig.dispatch(
                        AuthEvent.UserAuthenticated(
                            authUser,
                            result.data.tokenBearer ?: ""
                        )
                    )
                    result.data.id?.let {
                        val clientInfo = ClientInfo(id = it)
                        storeUser.dispatch(
                            UserProfileEvent.SetClientProfile(clientInfo)
                        )
                    }
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = EstadoAutenticacao.AUTENTICADO
                        )
                    }
                    Log.d("LoginViewModel", "Login state set to AUTENTICADO")
                    callback.onSuccess()
                }

                is State.Error -> {
                    Log.e("LoginViewModel", "Login error: ${result.exception}")
                    storeConfig.store.dispatch(AuthEvent.AuthenticationError(result.exception))
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = EstadoAutenticacao.NAO_AUTENTICADO
                        )
                    }
                    Log.d("LoginViewModel", "Login state set to NAO_AUTENTICADO")
                    callback.onError(result.exception)
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

    sealed class ViewEvent : IViewEvent {
        class SetLoading(val state: Boolean) : ViewEvent()
        class SetState(val state: EstadoAutenticacao) : ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val loginState: EstadoAutenticacao? = null,
    ) : IViewState
}

interface LoginCallback {
    fun onSuccess()
    fun onError(exception: Exception)
}