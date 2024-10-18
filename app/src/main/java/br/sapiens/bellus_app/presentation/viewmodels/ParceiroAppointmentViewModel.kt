package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PutAppointmentUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParceiroAppointmentViewModel @Inject constructor(
    private val putAppointmentUseCase: PutAppointmentUseCase,
    private val getAppointmentUseCase: GetAppointmentsByEstablishmentIdUseCase,
) : BaseViewModel<ParceiroAppointmentViewModel.ViewState, ParceiroAppointmentViewModel.ViewEvent>() {

    init {
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Loading -> {
                loadApponitments()
            }

            is ViewEvent.UpdateAppointment -> {}
        }
    }

    private fun loadApponitments() {
        viewModelScope.launch {
            val establishmentId = "6RuMqAfYfjL14zmg8XiA"
            when (val result: State<List<AppointmentDTO>> =
                getAppointmentUseCase.invoke(establishmentId)) {
                is State.Success -> {
                    setState {
                        ViewState.LoadAppointments(result.data)
                    }
                }

                is State.Error -> {}
            }
        }
    }

    private fun updateAppointment(schema: PutAppointmentSchema) {

    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadAppointments(val appointments: List<AppointmentDTO>) : ViewState()
        data class UpdatedAppointment(val appointment: AppointmentDTO) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object UpdateAppointment : ViewEvent()
    }
}