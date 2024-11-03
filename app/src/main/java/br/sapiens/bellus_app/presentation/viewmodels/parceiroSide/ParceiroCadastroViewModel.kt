package br.sapiens.bellus_app.presentation.viewmodels.parceiroSide

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.EstadoEnum
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.dominio.usecase.establishment.PostEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.PutEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.professional.PostProfessionalUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParceiroCadastroViewModel @Inject constructor(
    private val postEstablishmentUseCase: PostEstablishmentUseCase,
    private val putEstablishmentUseCase: PutEstablishmentUseCase,
    private val postProfessionalUseCase: PostProfessionalUseCase,
    private val userStore: UserProfileStore,
    context: Context
) : BaseViewModel<ParceiroCadastroViewModel.ViewState, ParceiroCadastroViewModel.ViewEvent>() {

    private var submitData: ViewEvent.Submit? = null
    val context = context

    override fun createInitialState(): ViewState {
        Log.d("ParceiroCadastroViewModel", "createInitialState called")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("ParceiroCadastroViewModel", "triggerEvent called with event: $event")
        when (event) {
            is ViewEvent.LoadUser -> {
                Log.d("ParceiroCadastroViewModel", "LoadUser event triggered")
            }

            is ViewEvent.Submit -> {
                Log.d("ParceiroCadastroViewModel", "Submit event triggered with data: $event")
                submitData = event
            }
        }
    }

    fun criarEstabelecimentoEProfissional() {
        Log.d("ParceiroCadastroViewModel", "criarEstabelecimentoEProfissional called")
        val evento = submitData

        if (evento != null) {
            Log.d("ParceiroCadastroViewModel", "Submit data is not null: $evento")
            val missingFields = mutableListOf<String>()

            if (evento.cnpj.isBlank()) missingFields.add("CNPJ")
            if (evento.nomeEstabelecimento.isBlank()) missingFields.add("Nome do Estabelecimento")
            if (evento.rua.isBlank()) missingFields.add("Rua")
            if (evento.numero.isBlank()) missingFields.add("Número")
            if (evento.cidade.isBlank()) missingFields.add("Cidade")
            if (evento.cep.isBlank()) missingFields.add("CEP")
            if (evento.telefone.isBlank()) missingFields.add("Telefone")

            if (missingFields.isNotEmpty()) {
                Log.d(
                    "ParceiroCadastroViewModel",
                    "Missing fields: ${missingFields.joinToString(", ")}"
                )
                setState {
                    ViewState.SubmitError(
                        "Preencha os seguintes campos obrigatórios: ${
                            missingFields.joinToString(
                                ", "
                            )
                        }"
                    )
                }
                return
            }

            val postSchema = PostEstablishmentSchema(
                cnpj = evento.cnpj,
                nome = evento.nomeEstabelecimento,
                endereco = EnderecoPartialModel(
                    rua = evento.rua,
                    numero = evento.numero,
                    cidade = evento.cidade,
                    estado = evento.estado,
                    cep = evento.cep
                ),
                telefone = listOf(evento.telefone),
                horario_funcionamento = HorarioFuncionamento(
                    segunda_feira = Horario(abertura = "00:00", fechamento = "00:00"),
                    terca_feira = Horario(abertura = "00:00", fechamento = "00:00"),
                    quarta_feira = Horario(abertura = "00:00", fechamento = "00:00"),
                    quinta_feira = Horario(abertura = "00:00", fechamento = "00:00"),
                    sexta_feira = Horario(abertura = "00:00", fechamento = "00:00"),
                    sabado = Horario(abertura = "00:00", fechamento = "00:00"),
                    domingo = Horario(abertura = "00:00", fechamento = "00:00"),
                ),
                imagem = listOf(""),
                portfolio = listOf(""),
                description = "Loren Ipsum",
                profissionais_filiados = listOf(""),
                profissional_dono = "",
            )
            Log.d("ParceiroCadastroViewModel", "PostEstablishmentSchema created: $postSchema")
            viewModelScope.launch {
                when (val result1: State<EstabelecimentoDTO> =
                    postEstablishmentUseCase.invoke(postSchema)) {
                    is State.Success -> {
                        Log.d(
                            "ParceiroCadastroViewModel",
                            "PostEstablishmentUseCase success: ${result1.data}"
                        )
                        val professionalSchema = userStore.getCurrentUserId()?.let {
                            PostProfessionalSchema(
                                user_id = it,
                                name = "",
                                profession = evento.profissional_profission,
                                linked_establishment_id = result1.data.id,
                                cpf = evento.cpf
                            )
                        }
                        Log.d(
                            "ParceiroCadastroViewModel",
                            "PostProfessionalSchema created: $professionalSchema"
                        )
                        when (val result2 = postProfessionalUseCase.invoke(professionalSchema)) {
                            is State.Success -> {
                                Log.d(
                                    "ParceiroCadastroViewModel",
                                    "PostProfessionalUseCase success: ${result2.data}"
                                )
                                val profissionalId = result1.data.id
                                val establishmentEncapsulation =
                                    PutEstablishmentSchemaEncapsulation(
                                        id = result1.data.id,
                                        PutEstablishmentSchema(
                                            cnpj = result1.data.cnpj,
                                            nome = result1.data.nome,
                                            endereco = result1.data.endereco,
                                            telefone = result1.data.telefone,
                                            horario_funcionamento = result1.data.horario_funcionamento,
                                            imagem = result1.data.imagem,
                                            portfolio = result1.data.portfolio,
                                            description = result1.data.description,
                                            profissionais_filiados = result1.data.profissionaisFiliados,
                                            profissional_dono = profissionalId,
                                        )
                                    )
                                Log.d(
                                    "ParceiroCadastroViewModel",
                                    "PutEstablishmentSchemaEncapsulation created: $establishmentEncapsulation"
                                )
                                when (val result3 =
                                    putEstablishmentUseCase.invoke(establishmentEncapsulation)) {
                                    is State.Success -> {
                                        Log.d(
                                            "ParceiroCadastroViewModel",
                                            "PutEstablishmentUseCase success: ${result3.data}"
                                        )
                                        setState {
                                            ViewState.SubmitSucess
                                        }
                                    }

                                    is State.Error -> {
                                        Log.e(
                                            "ParceiroCadastroViewModel",
                                            "PutEstablishmentUseCase error: ${result3.exception.message}"
                                        )
                                        setState {
                                            ViewState.SubmitError(
                                                result3.exception.message
                                                    ?: "Erro ao atualizar estabelecimento"
                                            )
                                        }
                                    }
                                }


                            }

                            is State.Error -> {
                                Log.e(
                                    "ParceiroCadastroViewModel",
                                    "PostProfessionalUseCase error: ${result2.exception.message}"
                                )
                                setState {
                                    ViewState.SubmitError(
                                        result2.exception.message
                                            ?: "Erro ao criar profissional"
                                    )
                                }
                            }
                        }

                    }

                    is State.Error -> {
                        Log.e(
                            "ParceiroCadastroViewModel",
                            "PostEstablishmentUseCase error: ${result1.exception.message}"
                        )
                        setState {
                            ViewState.SubmitError(
                                result1.exception.message
                                    ?: "Erro ao criar estabelecimento"
                            )
                        }
                    }
                }
            }
        } else {
            Log.d("ParceiroCadastroViewModel", "Submit data is null")
            setState {
                ViewState.SubmitError("Preencha todos os campos obrigatórios")
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data object SubmitSucess : ViewState()
        data class SubmitError(val message: String) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        object LoadUser : ViewEvent()
        data class Submit(
            val bairro: String,
            val rua: String,
            val numero: String,
            val complemento: String,
            val cnpj: String,
            val razaoSocial: String,
            val nomeEstabelecimento: String,
            val telefone: String,
            val profissional_profission: String,
            val cidade: String,
            val estado: EstadoEnum,
            val cep: String,
            val cpf: String,
        ) : ViewEvent()
    }

}