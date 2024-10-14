package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.GetAppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByClientIdUseCase
import br.sapiens.bellus_app.dominio.usecase.appointment.PostAppointmentUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerAppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase, // TODO: Usara para modificar o agendamento
    private val storeUser: UserProfileStore,
    private val storeMarketplace: MarketplaceStore,
    private val getAppointmentsUseCase: GetAppointmentsByClientIdUseCase,
    private val getEstablishmentDetailsUsecase: GetEstablishmentByIdUseCase,
    private val getServiceUseCase: GetServiceByIdUseCase,
) : BaseViewModel<ManagerAppointmentViewModel.ViewState, ManagerAppointmentViewModel.ViewEvent>() {

    private val _serviceList = mutableListOf<ServiceDTO>()
    val serviceList: List<ServiceDTO> get() = _serviceList

    init {
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("ManagerAppointmentViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("ManagerAppointmentViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.Loading -> listAppointments()
            is ViewEvent.LoadedAppointments -> {}
        }
    }

//    suspend fun getServiceScheduleTime(id: String): String {s
//        Log.d(
//            "ManagerAppointmentViewModel",
//            "função getEstablishmentsName foi invocada"
//        )
//        return withContext(Dispatchers.IO) {
//            var name = ""
//            when (val result: State<EstabelecimentoDTO> =
//                getEstablishmentDetailsUsecase.invoke(id)) {
//                is State.Success -> {
//                    name = result.data.nome // É o seguinte, simplemente essa função não tá invocada
//                    Log.d(
//                        "ManagerAppointmentViewModel",
//                        "getEstablishmentsName: $name"
//                    )
//                }
//
//                is State.Error -> {}
//            }
//            name
//        }
//    }

    suspend fun getEstablishmentsName(id: String): String {
        Log.d(
            "ManagerAppointmentViewModel",
            "função getEstablishmentsName foi invocada"
        )
        return withContext(Dispatchers.IO) {
            var name = ""
            when (val result: State<EstabelecimentoDTO> =
                getEstablishmentDetailsUsecase.invoke(id)) {
                is State.Success -> {
                    name = result.data.nome // É o seguinte, simplemente essa função não tá invocada
                    Log.d(
                        "ManagerAppointmentViewModel",
                        "getEstablishmentsName: $name"
                    )
                }

                is State.Error -> {}
            }
            name
        }
    }

    suspend fun getServicePrice(id: String): Float {
        return withContext(Dispatchers.IO) {
            var price = 0f
            when (val result: State<ServiceDTO> = getServiceUseCase.invoke(id)) {
                is State.Success -> {
                    price = result.data.price
                }

                is State.Error -> {}
            }
            price
        }

    }

    private fun listAppointments() {
        viewModelScope.launch {
            val userId = storeUser.getCurrentUserId()
            if (userId != null) {
                when (val result: State<List<GetAppointmentDTO>> =
                    getAppointmentsUseCase.invoke(userId)) {
                    is State.Success -> {
                        Log.d("ManagerAppointmentViewModel", "Appointments loaded successfully")
                        setState {
                            ViewState.LoadedAppointments(result.data)
                        }
                    }

                    is State.Error -> {
                        Log.e(
                            "ManagerAppointmentViewModel",
                            "Error loading appointments: ${result.exception}"
                        )
                        setState {
                            ViewState.Loading
                        }
                    }
                }
            } else {
                Log.w("ManagerAppointmentViewModel", "User ID is null")
                setState {
                    ViewState.Error(Exception("User ID is null"))
                }
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadedAppointments(val appointments: List<GetAppointmentDTO>) : ViewState()
        data class Error(val exception: Exception) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data class LoadedAppointments(val appointments: List<GetAppointmentDTO>) : ViewEvent()
    }
}