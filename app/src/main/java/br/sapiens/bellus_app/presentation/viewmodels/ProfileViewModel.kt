package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.ProfessionalInfo
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
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

    suspend fun getProfessionalInfo(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            Log.d("ProfileViewModel", "Fetching professional info")
            val userId = userStore.getCurrentUserId()
            Log.d("ProfileViewModel", "Current user ID: $userId")
            when (val result: State<ProfessionalDTO> = professionalByUserIdUseCase.invoke(userId)) {
                is State.Success -> {
                    val professional: ProfessionalDTO = result.data
                    Log.d(
                        "ProfileViewModel",
                        "Professional info fetched successfully: $professional"
                    )
                    if (professional.id.isNotEmpty()) {
                        Log.d(
                            "ProfileViewModel",
                            "Professional ID is not empty: ${professional.id}"
                        )
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
                        Log.d("ProfileViewModel", "State updated with professional info")
                        userStore.dispatch(
                            UserProfileEvent.SetProfessionalProfile(
                                ProfessionalInfo(
                                    id = professional.id,
                                    userId = professional.userId,
                                    establishmentId = professional.linkedEstablishmentId,
                                    name = professional.name,
                                    email = "Pegar do endpoint do usuario"
                                )
                            )
                        ).also {
                            Log.d("ProfileViewModel", "Dispatched SetProfessionalProfile event")
                            // Verifica se o estado foi atualizado corretamente
                            val updatedProfile = userStore.getActiveProfileType()
                            if (updatedProfile == UserProfileType.PROFESSIONAL) {
                                Log.d(
                                    "ProfileViewModel",
                                    "Profile PROFISSIONAL updated successfully"
                                )
                                onSuccess() // Chama o callback de sucesso para navegação
                            } else {
                                Log.e("ProfileViewModel", "Failed to update profile")
                                onError() // Chama o callback de erro para navegação
                            }
                        }
                        Log.d("ProfileViewModel", "Dispatched SetProfessionalProfile event")
                        onSuccess() // Chama o callback de sucesso para navegação
                        Log.d("ProfileViewModel", "onSuccess callback called")
                    }
                }

                is State.Error -> {
                    Log.e(
                        "ProfileViewModel",
                        "Error fetching professional info: ${result.exception}"
                    )
                    setState { ViewState.Error("Erro ao obter informações do profissional") }
                    onError() // Chama o callback de erro para navegação
                    Log.d("ProfileViewModel", "onError callback called")
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