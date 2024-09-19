package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.GetReviewsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.GetServicesByEstablishmentUseCase
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDetail
import br.sapiens.bellus_app.utils.toReviewsDetails
import br.sapiens.bellus_app.utils.toServiceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceSelectionViewModel @Inject constructor(
    private val marketplaceStore: MarketplaceStore,
    private val serviceUseCase: GetServicesByEstablishmentUseCase,
    private val establishmentUseCase: GetEstablishmentByIdUseCase,
    private val reviewsUseCase: GetReviewsByEstablishmentIdUseCase,
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
            val establishmentDetails: List<AvailableEstablishment> =
                marketplaceStore.getEstablishmentDetails()
            val serviceDetails: List<ServiceDetails> = marketplaceStore.getServiceDetails()
            val reviewsDetails: List<ReviewsDetails> = marketplaceStore.getReviews()

            Log.d("ServiceSelectionVM", "EstablishmentDetails: $establishmentDetails")
            Log.d("ServiceSelectionVM", "ServiceDetails: $serviceDetails")

            if (establishmentDetails.isNotEmpty() && serviceDetails.isNotEmpty() && reviewsDetails.isNotEmpty()) {
                setState {
                    ViewState.UserLoaded(establishmentDetails, serviceDetails, reviewsDetails)
                }
            } else {
                // Aqui começa a lista dos estabelecimentos
                val establishmentId: String = marketplaceStore.getEstablishmentItemId().toString()
                Log.d("ServiceSelectionVM", "EstablishmentId: $establishmentId")
                val establishmentState: State<EstabelecimentoDTO> =
                    establishmentUseCase.execute(establishmentId)

                Log.d("ServiceSelectionVM", "EstablishingState: $establishmentState")

                val establishmentDetail: EstablishmentDetail = when (establishmentState) {
                    is State.Success -> establishmentState.data.toEstablishmentDetail()
                    is State.Error -> TODO("Error a vista! ServiceSelectionVM")
                }

                // Aqui começa a lista dos serviços
                val servicesState: State<List<ServiceDTO>> =
                    serviceUseCase.execute(
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

                // Aqui começa a lista das avaliações
                // TODO: ESTAMOS SOBRECARREGANDO ESSA VIEWMODEL MAS FDS
                val reviewsList: State<List<ReviewsDTO>> = reviewsUseCase.execute(establishmentId)
                val reviewsState: List<ReviewsDetails> =
                    when (reviewsList) {
                        is State.Success -> reviewsList.data.map { it.toReviewsDetails() }
                        is State.Error -> TODO("AAAAAAAAAAAAAAAAAAA")
                    }

                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessGetEstablishmentCurrent(
                        establishmentDetail
                    )
                )
                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessServiceDetails(
                        newServiceDetails
                    )
                )

                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessGetReviews(
                        reviewsState
                    )
                )

                // CASO dÊ ELSE significa establishmentDetails e serviceDetails são vazios
                val newEstablishmentDetails = marketplaceStore.getEstablishmentDetails()
                val updatedServiceDetails = marketplaceStore.getServiceDetails()
                Log.d("ServiceSelectionVM", "NewEstablishmentDetails: $establishmentDetails")
                Log.d("ServiceSelectionVM", "UpdatedServiceDetails: $serviceDetails")

                setState {
                    ViewState.UserLoaded(
                        newEstablishmentDetails,
                        updatedServiceDetails,
                        reviewsState
                    )
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
            val establishmentDetails: List<AvailableEstablishment>,
            val serviceDetails: List<ServiceDetails>,
            val reviewsDetails: List<ReviewsDetails>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}