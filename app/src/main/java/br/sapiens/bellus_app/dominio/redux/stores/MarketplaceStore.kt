package br.sapiens.bellus_app.dominio.redux.stores

import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.MarketplaceReducer
import br.sapiens.bellus_app.dominio.redux.updater.MarketplaceStateUpdater
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketplaceStore @Inject constructor(
    private val marketplaceReducer: MarketplaceReducer,
    private val marketplaceStateUpdater: MarketplaceStateUpdater,
    coroutineScope: CoroutineScope
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    init {
        coroutineScope.launch(Dispatchers.Default) {
            marketplaceReducer.reducer(store).collectLatest { newState ->
                store.updateState(newState)
            }
        }
    }

    suspend fun dispatch(event: MarketplaceEvent) {
        val currentState: ApplicationState = store.stateFlow.value
        val newMarketplaceState = marketplaceStateUpdater.update(event, currentState)
        store.updateState(newMarketplaceState)
    }

    fun getEstablishmentsDetails(): List<AvailableEstablishment> {
        return store.stateFlow.value.marketplaceState.establishmentSummaries
    }

    fun getCurrentEstablishment(): EstablishmentDetail? {
        return store.stateFlow.value.marketplaceState.currentEstablishment
    }

    fun getServiceDetails(): List<ServiceDetails> {
        return store.stateFlow.value.marketplaceState.serviceDetails
    }

    fun getEstablishmentItemId(): String? {
        return store.stateFlow.value.marketplaceState.currentEstablishmentItemId
    }

    fun getReviews(): List<ReviewsDetails> {
        return store.stateFlow.value.marketplaceState.reviewsCurrentEstablishment ?: emptyList()
    }

    suspend fun setEstablishmentItemId(id: String) {
        val currentState = store.stateFlow.value
        val newMarketplaceState =
            currentState.marketplaceState.copy(currentEstablishmentItemId = id)
        val newState = currentState.copy(marketplaceState = newMarketplaceState)
        store.updateState(newState)
    }
}
