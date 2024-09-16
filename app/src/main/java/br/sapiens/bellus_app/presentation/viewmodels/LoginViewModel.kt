package br.sapiens.bellus_app.presentation.viewmodels


import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AuthDTO
import br.sapiens.bellus_app.dominio.model.AuthEvent
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.redux.stores.AuthStore
import br.sapiens.bellus_app.dominio.usecase.LoginUseCase
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val storeConfig: AuthStore
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.ViewEvent>() {

    fun loginWithCredential(authCredential: AuthCredential) {
        setState { state.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result: State<AuthDTO> =
                loginUseCase.execute(LoginUseCase.Input(authCredential = authCredential))) {
                is State.Success -> {
                    val authUser = AuthUser(result.data.id ?: "")
                    storeConfig.dispatch(
                        AuthEvent.UserAuthenticated(
                            authUser,
                            result.data.tokenBearer ?: ""
                        )
                    )
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = EstadoAutenticacao.AUTENTICADO
                        )
                    }
                }

                is State.Error -> {
                    storeConfig.store.dispatch(AuthEvent.AuthenticationError(result.exception))
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = EstadoAutenticacao.NAO_AUTENTICADO
                        )
                    }
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