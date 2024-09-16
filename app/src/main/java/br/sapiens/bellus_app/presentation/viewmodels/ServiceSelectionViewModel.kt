package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.model.MarketplaceAction
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.GetServicesByEstablishmentUseCase
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toServiceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceSelectionViewModel @Inject constructor(
    private val marketplaceStore: MarketplaceStore,
    private val getServicesByEstablishmentUseCase: GetServicesByEstablishmentUseCase
) : BaseViewModel<ServiceSelectionViewModel.ViewState, ServiceSelectionViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    init {
        viewModelScope.launch {
            loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            Log.d("ServiceSelectionVM", "Iniciando loadUser")
            val establishmentDetails = marketplaceStore.getEstablishmentDetails()
            val serviceDetails = marketplaceStore.getServiceDetails()

            Log.d("ServiceSelectionVM", "EstablishmentDetails: $establishmentDetails")
            Log.d("ServiceSelectionVM", "ServiceDetails: $serviceDetails")

            if (establishmentDetails.isNotEmpty() && serviceDetails.isNotEmpty()) {
                setState {
                    ViewState.UserLoaded(establishmentDetails, serviceDetails)
                }
            } else {
                val establishmentId: String = marketplaceStore.getEstablishmentItemId().toString()
                Log.d("ServiceSelectionVM", "EstablishmentId: $establishmentId")

                val servicesState: State<List<ServiceDTO>> =
                    getServicesByEstablishmentUseCase.execute(
                        GetServicesByEstablishmentUseCase.Input(establishmentId)
                    )
                Log.d("ServiceSelectionVM", "ServicesState: $servicesState")

                val services: List<ServiceDTO> = when (servicesState) {
                    is State.Success -> servicesState.data
                    else -> emptyList()
                }
                Log.d("ServiceSelectionVM", "Services: $services")

                val newServiceDetails: List<ServiceDetails> = services.map { it.toServiceDetails() }
                Log.d("ServiceSelectionVM", "NewServiceDetails: $newServiceDetails")

                marketplaceStore.dispatch(
                    MarketplaceAction.UpdateServiceDetails(
                        newServiceDetails
                    )
                )

                val newEstablishmentDetails = marketplaceStore.getEstablishmentDetails()
                val updatedServiceDetails = marketplaceStore.getServiceDetails()
                Log.d("ServiceSelectionVM", "NewEstablishmentDetails: $newEstablishmentDetails")
                Log.d("ServiceSelectionVM", "UpdatedServiceDetails: $updatedServiceDetails")

                setState {
                    ViewState.UserLoaded(newEstablishmentDetails, updatedServiceDetails)
                }
            }
        }
    }

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.LoadUser -> loadUser()
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val establishmentDetails: List<EstablishmentDetails>,
            val serviceDetails: List<ServiceDetails>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}