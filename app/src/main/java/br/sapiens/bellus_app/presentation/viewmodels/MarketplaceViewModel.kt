package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.dominio.model.event.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentsSummariesUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsSummaryByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.user.GetUserUseCase
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toAvailableEstablishment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val establishmentSummariesUseCase: GetEstablishmentsSummariesUseCase,
    private val reviewsSummaryUseCase: GetReviewsSummaryByEstablishmentIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val marketplaceStore: MarketplaceStore,
    private val userDataStore: UserProfileStore
) : BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    //    var username: String? = null
    var username = mutableStateOf("")

    init {
        triggerEvent(ViewEvent.AvailableEstablishmentsLoad)
    }


    override fun createInitialState(): ViewState {
        Log.d("MarketplaceViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("MarketplaceViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.AvailableEstablishmentsLoad -> loadUser()
            is ViewEvent.LoadUserName -> {
                getUserName()
            }
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            val clientInfoName = userDataStore.getCurrentClientInfoName()
            if (!clientInfoName.isNullOrEmpty()) {
                setState { ViewState.UserLoaded(clientInfoName) }
                username.value = clientInfoName
            } else {
                when (val result = getUserUseCase.invoke(null)) {
                    is State.Success -> {
                        userDataStore.setNameClientInfo(result.data.name)
                        Log.d("MarketplaceViewModel", "User Profile name: ${result.data.name}")
                        setState { ViewState.UserLoaded(result.data.name) }
                        username.value = result.data.name
                    }

                    is State.Error -> {

                    }
                }
            }
        }
    }

//    private fun loadUser() {
//        viewModelScope.launch {
//            Log.d("MarketplaceViewModel", "loadUser: Setting state to Loading")
//            setState { ViewState.Loading }
//
//            val establishmentDetails =
//                marketplaceStore.store.stateFlow.value.marketplaceState.establishmentSummaries
//            Log.d(
//                "MarketplaceViewModel",
//                "loadUser: Establishment details size: ${establishmentDetails.size}"
//            )
//
//            if (establishmentDetails.isNotEmpty()) {
//                Log.d("MarketplaceViewModel", "loadUser: Establishment details are not empty")
//                setState { ViewState.availableEstablishmentsLoad(establishmentDetails) }
//            } else {
//                Log.d(
//                    "MarketplaceViewModel",
//                    "loadUser: Establishment details are empty, fetching from use case"
//                )
//                when (val result: State<List<EstabelecimentoSummaryDTO>> =
//                    establishmentSummariesUseCase.execute(null)) {
//                    is State.Success -> {
//                        Log.d(
//                            "MarketplaceViewModel",
//                            "loadUser: Successfully fetched establishment summaries"
//                        )
//                        val establishmentAvailables: List<AvailableEstablishment> =
//                            result.data.map {
//                                var averageReviews = 0.0F
//                                when (val reviewsSummary = reviewsSummaryUseCase.invoke(it.id)) {
//                                    is State.Success -> {
//                                        averageReviews = reviewsSummary.data.average_rating
//                                        Log.d(
//                                            "MarketplaceViewModel",
//                                            "loadUser: Successfully fetched reviews for establishment id ${it.id}, average rating: $averageReviews"
//                                        )
//                                    }
//
//                                    is State.Error -> {
//                                        Log.e(
//                                            "MarketplaceViewModel",
//                                            "loadUser: Error fetching reviews for establishment id ${it.id}"
//                                        )
//                                    }
//                                }
//                                it.toAvailableEstablishment(averageReviews)
//                            }
//                        marketplaceStore.dispatch(
//                            MarketplaceEvent.SuccessGetEstablishmentSummary(establishmentAvailables)
//                        )
//                        setState { ViewState.availableEstablishmentsLoad(establishmentAvailables) }
//                    }
//
//                    is State.Error -> {
//                        Log.e(
//                            "MarketplaceViewModel",
//                            "loadUser: Error loading user data: ${result.exception}"
//                        )
//                        setState { ViewState.Loading }
//                    }
//                }
//            }
//        }
//    }

    private fun loadUser() {
        viewModelScope.launch {
            Log.d("MarketplaceViewModel", "loadUser: Setting state to Loading")
            setState { ViewState.Loading }

            // Remover a verificação do marketplaceStore
            // val establishmentDetails = marketplaceStore.store.stateFlow.value.marketplaceState.establishmentSummaries

            // if (establishmentDetails.isNotEmpty()) {
            //     Log.d("MarketplaceViewModel", "loadUser: Establishment details are not empty")
            //     setState { ViewState.availableEstablishmentsLoad(establishmentDetails) }
            // } else {
            Log.d(
                "MarketplaceViewModel",
                "loadUser: Establishment details are empty, fetching from use case"
            )
            when (val result: State<List<EstabelecimentoSummaryDTO>> =
                establishmentSummariesUseCase.execute(null)) {
                is State.Success -> {
                    Log.d(
                        "MarketplaceViewModel",
                        "loadUser: Successfully fetched establishment summaries"
                    )
                    val establishmentAvailables: List<AvailableEstablishment> = result.data.map {
                        var averageReviews = 0.0F
                        when (val reviewsSummary = reviewsSummaryUseCase.invoke(it.id)) {
                            is State.Success -> {
                                averageReviews = reviewsSummary.data.average_rating
                                Log.d(
                                    "MarketplaceViewModel",
                                    "loadUser: Successfully fetched reviews for establishment id ${it.id}, average rating: $averageReviews"
                                )
                            }

                            is State.Error -> {
                                Log.e(
                                    "MarketplaceViewModel",
                                    "loadUser: Error fetching reviews for establishment id ${it.id}"
                                )
                            }
                        }
                        it.toAvailableEstablishment(averageReviews)
                    }
                    marketplaceStore.dispatch(
                        MarketplaceEvent.SuccessGetEstablishmentSummary(
                            establishmentAvailables
                        )
                    )
                    setState { ViewState.availableEstablishmentsLoad(establishmentAvailables) }
                }

                is State.Error -> {
                    Log.e(
                        "MarketplaceViewModel",
                        "loadUser: Error loading user data: ${result.exception}"
                    )
                    setState { ViewState.Loading }
                }
            }
            // }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class availableEstablishmentsLoad(
            val availableEstablishments: List<AvailableEstablishment>
        ) : ViewState()

        data class UserLoaded(val name: String) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object AvailableEstablishmentsLoad : ViewEvent()
        data object LoadUserName : ViewEvent()
    }
}