package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.dominio.model.event.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostReviewsSchema
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsSummaryByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.review.PostReviewsUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServicesByEstablishmentUseCase
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDetail
import br.sapiens.bellus_app.utils.toReviewsDetails
import br.sapiens.bellus_app.utils.toServiceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceSelectionViewModel @Inject constructor(
    private val serviceUseCase: GetServicesByEstablishmentUseCase,
    private val establishmentUseCase: GetEstablishmentByIdUseCase,
    private val reviewsUseCase: GetReviewsByEstablishmentIdUseCase,
    private val reviewsSummaryUseCase: GetReviewsSummaryByEstablishmentIdUseCase,
    private val postReviewsUseCase: PostReviewsUseCase,
    private val marketplaceStore: MarketplaceStore,
    private val userStore: UserProfileStore,
    coroutineScope: CoroutineScope
) : BaseViewModel<MarketplaceSelectionViewModel.ViewState, MarketplaceSelectionViewModel.ViewEvent>() {

    val mkt = marketplaceStore
    val scope = coroutineScope

    init {
        viewModelScope.launch {
            loadUser()
        }
    }


    fun submitReview(comment: String, rating: Float) {
        viewModelScope.launch {
            val currentEstablishmentId = marketplaceStore.getCurrentEstablishment()?.id
            val userId = userStore.getCurrentUserId()

            if (!currentEstablishmentId.isNullOrEmpty() && !userId.isNullOrEmpty()) {
                val schema = PostReviewsSchema(
                    establishment_id = currentEstablishmentId,
                    user_id = userId,
                    rating = rating,
                    comment = comment
                )
                when (val result = postReviewsUseCase.invoke(schema)) {
                    is State.Success -> {

                    }

                    is State.Error -> {
                        
                    }
                }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            Log.d("ServiceSelectionVM", "Iniciando loadUser")
            val currentEstablishment: EstablishmentDetail? =
                marketplaceStore.getCurrentEstablishment()
            Log.d("ServiceSelectionVM", currentEstablishment.toString())
            val serviceDetails: List<ServiceDetails> = marketplaceStore.getServiceDetails()
            val reviewsDetails: List<ReviewsDetails> = marketplaceStore.getReviews()

            Log.d("ServiceSelectionVM", "EstablishmentDetails: $currentEstablishment")
            Log.d("ServiceSelectionVM", "ServiceDetails: $serviceDetails")

            if (currentEstablishment != null && serviceDetails.isNotEmpty() && reviewsDetails.isNotEmpty()) {
                // O primeiro estado deles sempre vai ser null
                setState {
                    ViewState.UserLoaded(
                        currentEstablishment,
                        serviceDetails,
                        reviewsDetails
                    )
                }
            } else {
                // Aqui começa o estabelecimento
                Log.d("ServiceSelectionVM", "Else")
                val establishmentId: String = marketplaceStore.getEstablishmentItemId().toString()
                Log.d("ServiceSelectionVM", "EstablishmentId: $establishmentId")
                val currentEstablishmentState: State<EstabelecimentoDTO> =
                    establishmentUseCase.execute(establishmentId)

                Log.d("ServiceSelectionVM", "EstablishingState: $currentEstablishmentState")

                val currentEstablishmentDetails: EstablishmentDetail =
                    when (currentEstablishmentState) {
                        is State.Success -> {
                            var totalReviews = 0
                            var average_rating = 0.0F
                            when (val totalReviewsState: State<ReviewsSummary> =
                                reviewsSummaryUseCase.invoke(currentEstablishmentState.data.id)) {
                                is State.Success -> {
                                    totalReviews = totalReviewsState.data.total_reviews
                                    average_rating = totalReviewsState.data.average_rating
                                }

                                is State.Error -> TODO("Pao de batata")
                            }
                            currentEstablishmentState.data.toEstablishmentDetail(
                                totalReviews,
                                average_rating
                            )
                        }

                        is State.Error -> TODO("Error a vista! ServiceSelectionVM")
                    }
                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessGetEstablishmentCurrent(
                        currentEstablishmentDetails
                    )
                )
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

                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessServiceDetails(
                        newServiceDetails
                    )
                )
                // Aqui começa a lista das avaliações
                // TODO: ESTAMOS SOBRECARREGANDO ESSA VIEWMODEL MAS FDS
                val reviewsList: State<List<ReviewsDTO>> = reviewsUseCase.execute(establishmentId)
                val reviewsState: List<ReviewsDetails> =
                    when (reviewsList) {
                        is State.Success -> reviewsList.data.map { it.toReviewsDetails() }
                        is State.Error -> TODO("AAAAAAAAAAAAAAAAAAA")
                    }
                marketplaceStore.dispatch(
                    MarketplaceEvent.SuccessGetReviews(
                        reviewsState
                    )
                )

                Log.d("ServiceSelectionVM", "Deu bom!")
                setState {
                    ViewState.UserLoaded(
                        currentEstablishmentDetails,
                        newServiceDetails,
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
            val establishmentDetails: EstablishmentDetail,
            val serviceDetails: List<ServiceDetails>,
            val reviewsDetails: List<ReviewsDetails>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}