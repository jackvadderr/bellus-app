//package br.sapiens.bellus_app.presentation.viewmodels
//
//import android.util.Log
//import br.sapiens.bellus_app.base.BaseViewModel
//import br.sapiens.bellus_app.base.IViewEvent
//import br.sapiens.bellus_app.base.IViewState
//import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
//import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
//import br.sapiens.bellus_app.dominio.usecase.appointment.GetAppointmentsByClientIdUseCase
//import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
//import br.sapiens.bellus_app.dominio.usecase.service.GetServiceByIdUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class ParceiroSelectedAppointmentManager @Inject constructor(
//
//) : BaseViewModel<ParceiroSelectedAppointmentManager.ViewState, ParceiroSelectedAppointmentManager.ViewEvent>() {
//
//    private val _serviceList = mutableListOf<ServiceDTO>()
//    val serviceList: List<ServiceDTO> get() = _serviceList
//
//    init {
//        triggerEvent(ViewEvent.Loading)
//    }
//
//    override fun createInitialState(): ViewState {
//        Log.d("ManagerAppointmentViewModel", "Creating initial state")
//        return ViewState.Loading
//    }
//
//    override fun triggerEvent(event: ViewEvent) {
//        Log.d("ManagerAppointmentViewModel", "Triggering event: $event")
//        when (event) {
//            is ViewEvent.Loading -> {
//
//            }
//        }
//    }
//
//
//    sealed class ViewState : IViewState {
//        data object Loading : ViewState()
//    }
//
//    sealed class ViewEvent : IViewEvent {
//        data object Loading : ViewEvent()
//
//    }
//}