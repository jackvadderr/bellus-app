package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.dominio.usecase.CadastroUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CadastroViewModel @Inject constructor(
    private val cadastroUseCase: CadastroUseCase,
) : BaseViewModel<CadastroViewModel.ViewState, CadastroViewModel.ViewEvent>() {
    override fun createInitialState(): ViewState = ViewState()

    private fun cadastrar() {
        viewModelScope.launch {
            when (val response = cadastroUseCase.execute(
                CadastroUseCase.Input(
                    uuid = state.uuid,
                    name = state.name,
                    phone = state.phone,
                    email = state.email,
                    genero = state.genero,
                    senha = state.senha
                )
            )) {
                is State.Success -> {
                    response.data
                }
                is State.Error -> {
                    response.exception
                }
            }
        }
    }

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            when (event) {
                /*
                 * EXECUTAR O CADASTRO
                 */
                is ViewEvent.Event -> {
                    cadastrar()
                }
                /*
                 * UUID
                 */
                is ViewEvent.SetUUID -> {
                    setState {
                        state.copy(
                            uuid = event.text
                        )
                    }
                }
                /*
                 * NAME
                 */
                is ViewEvent.SetName -> {
                    setState {
                        state.copy(
                            name = event.text
                        )
                    }
                }
                /*
                 * EMAIL
                 */
                is ViewEvent.SetEmail -> {
                    setState {
                        state.copy(
                            email = event.text
                        )
                    }
                }
                /*
                 * SENHA
                 */
                is ViewEvent.SetSenha -> {
                    setState {
                        state.copy(
                            senha = event.text
                        )
                    }
                }
                /*
                 * TELEFONE
                 */

                is ViewEvent.SetTelefone -> {
                    setState {
                        state.copy(
                            phone = event.text
                        )
                    }
                }
                /*
                 * GENERO
                 */
                is ViewEvent.SetGenero -> {
                    setState {
                        state.copy(
                            genero = GeneroEnum.valueOf(event.text)
                        )
                    }
                }

                is ViewEvent.SetNewsletterCheck -> {
                    setState {
                        state.copy(
                            newsletterCheck = event.status
                        )
                    }
                }
                is ViewEvent.SetTermsCheck -> {
                    setState {
                        state.copy(
                            termsCheck = event.status
                        )
                    }
                }
            }
        }
    }

    sealed class ViewEvent : IViewEvent {
        class SetUUID(val text: String) : ViewEvent()
        class SetName(val text: String) : ViewEvent()
        class SetTelefone(val text: String) : ViewEvent()
        class SetGenero(val text: String) : ViewEvent()
        class SetEmail(val text: String) : ViewEvent()
        class SetSenha(val text: String) : ViewEvent()
        class SetTermsCheck(val status: Boolean) : ViewEvent()
        class SetNewsletterCheck(val status: Boolean) : ViewEvent()
        data object Event : ViewEvent()
    }

    data class ViewState(
        val uuid: String = "",
        val name: String = "",
        val phone: String = "",
        val genero: GeneroEnum = GeneroEnum.MASCULINO,
        val email: String = "",
        val senha: String = "",
        val termsCheck: Boolean = false,
        val newsletterCheck: Boolean = false,
        val isDisplay: Boolean = false,
        val isLoading: Boolean = false,
    ) : IViewState
}