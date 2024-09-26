package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.GetEstablishmentsSummariesUseCase
import br.sapiens.bellus_app.dominio.usecase.GetReviewsSummaryByEstablishmentIdUseCase
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
    private val marketplaceStore: MarketplaceStore,
) : BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    init {
        triggerEvent(ViewEvent.LoadUser)
    }


    override fun createInitialState(): ViewState {
        Log.d("MarketplaceViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("MarketplaceViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            setState { ViewState.Loading }

            val establishmentDetails =
                marketplaceStore.store.stateFlow.value.marketplaceState.establishmentSummaries
            if (establishmentDetails.isNotEmpty()) {
                setState { ViewState.UserLoaded(establishmentDetails) }
            } else {
                when (val result: State<List<EstabelecimentoSummaryDTO>> =
                    establishmentSummariesUseCase.execute(null)) {
                    is State.Success -> {
                        val establishmentAvailables: List<AvailableEstablishment> =
                            result.data.map {
                                var averageReviews = 0.0F
                                when (val reviewsSummary = reviewsSummaryUseCase.invoke(it.id)) {
                                    is State.Success -> {
                                        averageReviews = reviewsSummary.data.average_rating
                                    }

                                    is State.Error -> TODO()
                                }
                                it.toAvailableEstablishment(averageReviews)
                            }
                        marketplaceStore.dispatch(
                            MarketplaceEvent.SuccessGetEstablishmentSummary(establishmentAvailables)
                        )
                        setState { ViewState.UserLoaded(establishmentAvailables) }
                    }

                    is State.Error -> {
                        Log.e(
                            "MarketplaceViewModel",
                            "Error loading user data: ${result.exception}"
                        )
                        setState { ViewState.Loading }
                    }
                }
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val availableEstablishments: List<AvailableEstablishment>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}