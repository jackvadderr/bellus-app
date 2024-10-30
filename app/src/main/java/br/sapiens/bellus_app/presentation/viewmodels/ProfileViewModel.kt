package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.model.state.ProfessionalInfo
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.usecase.professional.GetProfessionalByUserIdUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userProfileStore: UserProfileStore,
    coroutineScope: CoroutineScope,
    private val professionalByUserIdUseCase: GetProfessionalByUserIdUseCase,
//    private val userDataStore: UserDataConfigManagerImpl,
//    context: Context
) : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.ViewEvent>() {

    val userStore = userProfileStore
    val coroutine = coroutineScope

    override fun createInitialState(): ViewState {
        Log.d("ProfileViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("ProfileViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.Loading -> {
                Log.d("ProfileViewModel", "Event is Loading")
            }
        }
    }

    suspend fun getProfessionalInfo() {
        Log.d("ProfileViewModel", "Getting professional info")
        viewModelScope.launch {
            val userId = userStore.getCurrentUserId()
            Log.d("ProfileViewModel", "User ID: $userId")
            when (val result: State<ProfessionalDTO> = professionalByUserIdUseCase.invoke(userId)) {
                is State.Success -> {
                    val professional: ProfessionalDTO = result.data
                    Log.d("ProfileViewModel", "Professional info retrieved: $professional")

                    if (!professional.id.isNullOrEmpty() ||
                        !professional.userId.isNullOrEmpty() ||
                        !professional.linkedEstablishmentId.isNullOrEmpty() ||
                        !professional.name.isNullOrEmpty() ||
                        !professional.profession.isNullOrEmpty()
                    ) {
                        Log.d("ProfileViewModel", "Professional info is valid")
                        setState {
                            ViewState.LoadedProfessionalInfo(
                                professionalInfo = ProfessionalInfo(
                                    id = professional.id,
                                    userId = professional.userId,
                                    establishmentId = professional.linkedEstablishmentId,
                                    name = professional.name,
                                    email = "Pegar do endpoint do usuario"
                                )
                            )
                        }
                    }
                }

                is State.Error -> {
                    Log.e(
                        "ProfileViewModel",
                        "Error retrieving professional info: ${result.exception.message}"
                    )
                    setState {
                        ViewState.Error("Erro ao obter informações do profissional")
                    }
                }
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadedProfessionalInfo(val professionalInfo: ProfessionalInfo) :
            ViewState()

        data class Error(val message: String) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
    }
}