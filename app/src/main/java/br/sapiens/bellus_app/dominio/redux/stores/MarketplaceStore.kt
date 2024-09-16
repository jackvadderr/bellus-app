package br.sapiens.bellus_app.dominio.redux.stores

import br.sapiens.bellus_app.dominio.model.MarketplaceAction
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.reducer.MarketplaceReducer
import br.sapiens.bellus_app.dominio.redux.updater.MarketplaceStateUpdater
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketplaceStore @Inject constructor(
    private val marketplaceReducer: MarketplaceReducer,
    private val marketplaceStateUpdater: MarketplaceStateUpdater
) {
    val store: Store<ApplicationState> = Store(ApplicationState())

    suspend fun dispatch(action: MarketplaceAction) {
        val currentState = store.stateFlow.value
        // Utiliza o reducer para calcular o novo estado do marketplace
        val newMarketplaceState = marketplaceReducer.reducer(currentState.marketplaceState, action)
        // Utiliza o updater para aplicar o novo estado de marketplace dentro do ApplicationState
        val newState = marketplaceStateUpdater.update(currentState, newMarketplaceState)
        // Atualiza o estado no Store
        store.updateState(newState)
    }

    fun getEstablishmentDetails(): List<EstablishmentDetails> {
        return store.stateFlow.value.marketplaceState.establishmentDetails
    }

    fun getServiceDetails(): List<ServiceDetails> {
        return store.stateFlow.value.marketplaceState.serviceDetails
    }

    fun getEstablishmentItemId(): String? {
        return store.stateFlow.value.marketplaceState.currenteEstablishmentItemId
    }

    suspend fun setEstablishmentItemId(id: String) {
        val currentState = store.stateFlow.value
        val newMarketplaceState =
            currentState.marketplaceState.copy(currenteEstablishmentItemId = id)
        val newState = currentState.copy(marketplaceState = newMarketplaceState)
        store.updateState(newState)
    }
}
