package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PutAppointmentSideEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParceiroAppointmentViewModel @Inject constructor(
    private val putAppointmentSideEstablishmentUseCase: PutAppointmentSideEstablishmentUseCase,
    private val getAppointmentsUseCase: GetAppointmentsByEstablishmentIdUseCase,
    private val getAppointmentDetailsUseCase: GetAppointmentByIdUseCase,
    private val getEstablishmentDetailsUsecase: GetEstablishmentByIdUseCase,
    private val getServiceUseCase: GetServiceByIdUseCase,
    private val userStore: UserProfileStore,
) : BaseViewModel<ParceiroAppointmentViewModel.ViewState, ParceiroAppointmentViewModel.ViewEvent>() {

    var globalEstablishmentId = mutableStateOf<String>("")
    var globalAppointmentId = mutableStateOf<String>("")

    init {
        Log.d("ParceiroAppointmentViewModel", "ViewModel initialized")
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("ParceiroAppointmentViewModel", "Creating initial state: Loading")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) { // Isso aqui vai carregar primeiro
        Log.d("ParceiroAppointmentViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.Loading -> {
                getEstablishmentId()
            }

            is ViewEvent.LoadAppointments -> {
                loadAppointments(globalEstablishmentId.value)
            }

            is ViewEvent.UpdateAppointment -> {
                updateAppointmentStatus(event.toString())
            }

            ViewEvent.LoadCurrentAppointment -> {
                
            }
        }
    }

    private fun getEstablishmentId() {
        Log.d("ParceiroAppointmentViewModel", "Getting user professional establishment ID")
        viewModelScope.launch {
            val establishmentId = userStore.getEstablishmentId()
            Log.d("ParceiroAppointmentViewModel", "Establishment ID: $establishmentId")
            if (!establishmentId.isNullOrEmpty()) {
                setState {
                    ViewState.EstablishmentId(establishmentId)
                }
                globalEstablishmentId.value = establishmentId
                triggerEvent(ViewEvent.LoadAppointments)
            }
        }
    }

    private fun loadAppointments(establishmentId: String) {
        Log.d(
            "ParceiroAppointmentViewModel",
            "Loading appointments for establishment ID: $establishmentId"
        )
        viewModelScope.launch {
            when (val result: State<List<AppointmentDTO>> =
                getAppointmentsUseCase.invoke(establishmentId)) {
                is State.Success -> {
                    Log.d("ParceiroAppointmentViewModel", "Appointments loaded successfully")
                    setState {
                        ViewState.LoadedAppointments(result.data)
                    }
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroAppointmentViewModel",
                        "Error loading appointments: ${result.exception.message}"
                    )
                    setState {
                        ViewState.Error
                    }
                }
            }
        }
    }

    private fun loadAppointment(id: String) {
        viewModelScope.launch {
            when (val result = getAppointmentDetailsUseCase.invoke(id)) {
                is State.Success -> {
                    setState {
                        ViewState.LoadedAppointment(result.data)
                    }
                }

                is State.Error -> {
                    setState {
                        ViewState.Error
                    }
                }
            }
        }
    }

    suspend fun getServiceStatus(id: String): String {
        Log.d("ParceiroAppointmentViewModel", "Getting service status for ID: $id")
        return withContext(Dispatchers.IO) {
            when (val result = getAppointmentDetailsUseCase.invoke(id)) {
                is State.Success -> {
                    Log.d(
                        "ParceiroAppointmentViewModel",
                        "Service status: ${result.data.statusRequest}"
                    )
                    result.data.statusRequest
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroAppointmentViewModel",
                        "Error getting service status: ${result.exception.message}"
                    )
                    "No Stringas"
                }
            }
        }
    }

    private fun updateAppointmentStatus(status: String) {
        Log.d("ParceiroAppointmentViewModel", "Updating appointment status to: $status")
        viewModelScope.launch {
            val schema = PutAppointmentSchema(
                status_request = status,
                completion_date = "2023-10-01"
            )
            val schemaEncapsulation = PutAppointmentSchemeEncapsulation(
                schema = schema,
                id = ""
            )

            when (val result: State<AppointmentDTO> =
                putAppointmentSideEstablishmentUseCase.invoke(schemaEncapsulation)) {
                is State.Success -> {
                    Log.d("ParceiroAppointmentViewModel", "Appointment status updated successfully")
                    setState {
                        ViewState.UpdateAppointment(result.data.statusRequest)
                    }
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroAppointmentViewModel",
                        "Error updating appointment status: ${result.exception.message}"
                    )
                    setState { ViewState.Error }
                }
            }
        }
    }

    suspend fun getEstablishmentsName(id: String): String {
        Log.d("ParceiroAppointmentViewModel", "Getting establishment name for ID: $id")
        return withContext(Dispatchers.IO) {
            var name = ""
            when (val result: State<EstabelecimentoDTO> =
                getEstablishmentDetailsUsecase.invoke(id)) {
                is State.Success -> {
                    name = result.data.nome
                    Log.d("ParceiroAppointmentViewModel", "Establishment name: $name")
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroAppointmentViewModel",
                        "Error getting establishment name: ${result.exception.message}"
                    )
                }
            }
            name
        }
    }

    suspend fun getServicePrice(id: String): Float {
        Log.d("ParceiroAppointmentViewModel", "Getting service price for ID: $id")
        return withContext(Dispatchers.IO) {
            var price = 0f
            when (val result: State<ServiceDTO> = getServiceUseCase.invoke(id)) {
                is State.Success -> {
                    price = result.data.price
                    Log.d("ParceiroAppointmentViewModel", "Service price: $price")
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroAppointmentViewModel",
                        "Error getting service price: ${result.exception.message}"
                    )
                }
            }
            price
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class EstablishmentId(val id: String) : ViewState()
        data class LoadedAppointments(val appointments: List<AppointmentDTO>) : ViewState()
        data class LoadedAppointment(val appointment: AppointmentDTO) : ViewState()
        data class UpdateAppointment(val status: String) : ViewState()
        data object Error : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object UpdateAppointment : ViewEvent()
        data object LoadAppointments : ViewEvent()
        data object LoadCurrentAppointment : ViewEvent()
    }
}