package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByClientIdUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerAppointmentViewModel @Inject constructor(
    private val storeUser: UserProfileStore,
    private val getAppointmentsUseCase: GetAppointmentsByClientIdUseCase,
    private val getEstablishmentDetailsUsecase: GetEstablishmentByIdUseCase,
    private val getServiceUseCase: GetServiceByIdUseCase,
) : BaseViewModel<ManagerAppointmentViewModel.ViewState, ManagerAppointmentViewModel.ViewEvent>() {

    init {
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState = ViewState.Loading

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Loading -> listAppointments()
            else -> {}
        }
    }

    suspend fun getEstablishmentName(id: String): String {
        return withContext(Dispatchers.IO) {
            (getEstablishmentDetailsUsecase.invoke(id) as? State.Success)?.data?.nome ?: ""
        }
    }

    suspend fun getServiceInfo(id: String): ServiceDTO {
        return withContext(Dispatchers.IO) {
            (getServiceUseCase.invoke(id) as? State.Success)?.data ?: ServiceDTO(
                id = "",
                name = "",
                description = "",
                duration = Duration("Hour", 0.0f),
                price = 0f,
                establishment_id = ""
            )
        }
    }

    private fun listAppointments() {
        viewModelScope.launch {
            val userId = storeUser.getCurrentUserId()
            if (userId != null) {
                when (val result = getAppointmentsUseCase.invoke(userId)) {
                    is State.Success -> setState { ViewState.LoadedAppointments(result.data) }
                    is State.Error -> setState { ViewState.Error(result.exception) }
                }
            } else {
                setState { ViewState.Error(Exception("User ID is null")) }
            }
        }
    }

    sealed class ViewState : IViewState {
        object Loading : ViewState()
        data class LoadedAppointments(val appointments: List<AppointmentDTO>) : ViewState()
        data class Error(val exception: Exception) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        object Loading : ViewEvent()
        data class LoadedAppointments(val appointments: List<AppointmentDTO>) : ViewEvent()
    }
}
