package br.sapiens.bellus_app.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.EstadoEnum
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.dominio.usecase.establishment.PostEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.professional.PostProfessionalUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParceiroCadastroViewModel @Inject constructor(
    private val postEstablishmentUseCase: PostEstablishmentUseCase,
    private val postProfessionalUseCase: PostProfessionalUseCase,
    private val userStore: UserProfileStore,
    context: Context
) : BaseViewModel<ParceiroCadastroViewModel.ViewState, ParceiroCadastroViewModel.ViewEvent>() {

    private var submitData: ViewEvent.Submit? = null
    val context = context

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoadUser -> {}
            is ViewEvent.Submit -> {
                submitData = event
            }
        }
    }

    fun criarEstabelecimento() {
        val evento = submitData

        if (evento != null &&
            evento.cnpj.isNotBlank() &&
            evento.nomeEstabelecimento.isNotBlank() &&
            evento.endereco.isNotBlank() &&
            evento.telefone.isNotBlank()
        ) {
            val postSchema = PostEstablishmentSchema(
                cnpj = "48180246000119",
                nome = evento.nomeEstabelecimento,
                endereco = EnderecoPartialModel(
                    rua = evento.endereco,
                    numero = evento.numero,
                    cidade = "Porto Velho",
                    estado = EstadoEnum.RO,
                    cep = "76804386",
                ),
                telefone = listOf("+5569992659304"),
                horario_funcionamento = HorarioFuncionamento(
                    segunda_feira = Horario(abertura = "08:00", fechamento = "10:00"),
                    terca_feira = Horario(abertura = "08:00", fechamento = "10:00"),
                    quarta_feira = Horario(abertura = "08:00", fechamento = "10:00"),
                    quinta_feira = Horario(abertura = "08:00", fechamento = "10:00"),
                    sexta_feira = Horario(abertura = "08:00", fechamento = "10:00"),
                    sabado = Horario(abertura = "08:00", fechamento = "10:00"),
                    domingo = Horario(abertura = "08:00", fechamento = "10:00"),
                ),
                imagem = listOf(""),
                portfolio = listOf(""),
                description = "Loren Ipsum",
                profissionais_filiados = listOf(""),
                profissional_dono = "",
            )
            viewModelScope.launch {
                when (val result = postEstablishmentUseCase.invoke(postSchema)) {
                    is State.Success -> {
                        val professionalSchema = userStore.getCurrentUserId()?.let {
                            PostProfessionalSchema(
                                user_id = it,
                                name = "Testando",
                                profession = "",
                                linked_establishment_id = ""
                            )
                        }
                        when (val result = postProfessionalUseCase.invoke(professionalSchema)) {
                            is State.Success -> {
                                setState {
                                    ViewState.SubmitSucess
                                }
                            }

                            is State.Error -> {}
                        }

                    }

                    is State.Error -> {
                        setState {
                            ViewState.SubmitError(
                                result.exception.message
                                    ?: "Erro ao criar profissional/estabelecimento"
                            )
                        }
                    }
                }
            }
        } else {
            setState {
                ViewState.SubmitError("")
            }
        }
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data object SubmitSucess : ViewState()
        data class SubmitError(val message: String) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
        data class Submit(
            val bairro: String,
            val endereco: String,
            val numero: String,
            val complemento: String,
            val cnpj: String,
            val razaoSocial: String,
            val nomeEstabelecimento: String,
            val telefone: String,
            val especialidade: String
        ) : ViewEvent()
    }
}