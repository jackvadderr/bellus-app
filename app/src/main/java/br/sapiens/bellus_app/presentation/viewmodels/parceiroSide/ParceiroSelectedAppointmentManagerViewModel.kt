package br.sapiens.bellus_app.presentation.viewmodels.parceiroSide

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.dominio.model.AppointmentDetail
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutAppointmentSchemeEncapsulation
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PutAppointmentSideEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.user.GetUserUseCase
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toAppointmentDetail
import br.sapiens.bellus_app.utils.toServiceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParceiroSelectedAppointmentManagerViewModel @Inject constructor(
    private val getAppointmentByIdUseCase: GetAppointmentByIdUseCase,
    private val putAppointment: PutAppointmentSideEstablishmentUseCase,
    private val getServiceById: GetServiceByIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val marketplaceStore: MarketplaceStore,
    private val userStore: UserProfileStore,
) : BaseViewModel<ParceiroSelectedAppointmentManagerViewModel.ViewState, ParceiroSelectedAppointmentManagerViewModel.ViewEvent>() {

    private val _serviceList = mutableListOf<ServiceDTO>()
    val serviceList: List<ServiceDTO> get() = _serviceList

    var supremeEstablishmentId: String? = null
    var supremeAppointmentId: String? = null
    var supremeServiceId: String? = null

    init {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "ViewModel initialized")
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "Creating initial state")
        return ViewState.Loading
    }


    override fun triggerEvent(event: ViewEvent) {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.Loading -> {
                Log.d("ParceiroSelectedAppointmentManagerViewModel", "Event: Loading")
                fetchAppointmentd()
                supremeAppointmentId?.let { id ->
                    if (id.isNotEmpty()) {
                        Log.d(
                            "ParceiroSelectedAppointmentManagerViewModel",
                            "Fetching appointment details for ID: $id"
                        )
                        getAppointmentDetails(id)
                    }
                }
            }

            is ViewEvent.UpdateAppointment -> {
                supremeAppointmentId?.let { id ->
                    if (id.isNotEmpty()) {
                        updateAppointment(
                            id = id,
                            newStatus = event.newStatus,
                            completionData = event.completionData
                        )
                    }
                }

            }
        }
    }

    private fun updateAppointment(id: String, newStatus: String, completionData: String) {
        viewModelScope.launch {
            val schema = PutAppointmentSchema(
                status_request = newStatus,
                completion_date = completionData
            )
            val encapsulation = PutAppointmentSchemeEncapsulation(
                id = id,
                schema = schema,
            )
            when (putAppointment.invoke(encapsulation)) {
                is State.Success -> {
                    Log.d(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Appointment updated successfully"
                    )
                }

                is State.Error -> {}
            }
        }
    }

    private fun getAppointmentDetails(id: String) {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "Fetching appointment details")
        viewModelScope.launch {
            when (val result = getAppointmentByIdUseCase.invoke(id)) {
                is State.Success -> {
                    val dto = result.data
                    val details = dto.toAppointmentDetail()
                    Log.d(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Appointment details fetched successfully: $details"
                    )
                    supremeServiceId = details.serviceId
                    Log.d(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Loaded service ID from appointment details: $supremeServiceId"
                    )
                    setState {
                        ViewState.LoadedCurrentAppointment(details)
                    }
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Error fetching appointment details",
                        result.exception
                    )
                }
            }
        }
    }

    private fun fetchAppointmentd() {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "Fetching service ID")
        marketplaceStore.store.stateFlow.value.marketplaceState.currentAppointmentId?.let { id ->
            if (id.isNotEmpty()) {
                supremeAppointmentId = id
                Log.d(
                    "ParceiroSelectedAppointmentManagerViewModel",
                    "Loaded appointment ID from marketplaceStore: $id"
                )
                setState {
                    ViewState.LoadedCurrentAppointmentId(id)
                }
            } else {
                Log.d(
                    "ParceiroSelectedAppointmentManagerViewModel",
                    "No appointment ID found in marketplaceStore"
                )
            }
        }
    }

    suspend fun getService(id: String): ServiceDetails? {
        Log.d("ParceiroSelectedAppointmentManagerViewModel", "Fetching service details for ID: $id")
        return withContext(Dispatchers.IO) {
            var service: ServiceDetails? = null
            when (val result = getServiceById.invoke(id)) {
                is State.Success -> {
                    val dto = result.data
                    val details = dto.toServiceDetails()
                    Log.d(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Service details fetched successfully: $details"
                    )
                    service = details
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Error fetching service details",
                        result.exception
                    )
                }
            }
            service
        }
    }

    suspend fun getUserInfo(): UserDTO? {
        return withContext(Dispatchers.IO) {
            var user: UserDTO? = null
            when (val result = getUserUseCase.invoke(null)) {
                is State.Success -> {
                    Log.d(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "User details fetched successfully: ${result.data}"
                    )
                    val data = result.data
                    data.let { user = result.data }
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroSelectedAppointmentManagerViewModel",
                        "Error fetching user details",
                        result.exception
                    )
                }
            }
            user
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadedCurrentAppointmentId(val id: String) : ViewState()
        data class LoadedCurrentAppointment(val appointment: AppointmentDetail) : ViewState()
        data class LoadedCurrentService(val service: ServiceDetails) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data class UpdateAppointment(
            val newStatus: String,
            val completionData: String
        ) : ViewEvent()

    }
}