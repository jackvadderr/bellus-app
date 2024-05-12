package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.sdk.AuthService
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authService: AuthService,
) : BaseViewModel<SplashViewModel.ViewState, SplashViewModel.ViewEvent>() {
    private var splashShowFlow = MutableStateFlow(true)
    var isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
            splashShowFlow.value = false
        }
        checkUser()
    }
    override fun createInitialState(): ViewState = ViewState()


    private fun checkUser() {
        viewModelScope.launch {
            delay(2000)
            authService.refreshUserToken(
                onSuccess = {
                val authState = if (authService.isUserLogin())
                    EstadoAutenticacao.AUTENTICADO
                else EstadoAutenticacao.NAO_AUTENTICADO

                triggerEvent(ViewEvent.SetAuthState(authState))
            }, onFailure = {
                triggerEvent(ViewEvent.SetAuthState(EstadoAutenticacao.NAO_AUTENTICADO))
            })
        }
    }

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            when (event) {
                ViewEvent.Event -> {
                    setState {
                        state.copy(
                            isLoading = true
                        )
                    }
                }

                is ViewEvent.SetAuthState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            authState = event.authState
                        )
                    }
                }
            }
        }
    }

    sealed class ViewEvent : IViewEvent {
        data object Event : ViewEvent()
        class SetAuthState(val authState: EstadoAutenticacao) : ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val authState: EstadoAutenticacao? = null,
    ) : IViewState
}