package br.sapiens.bellus_app.dominio.redux.updater

import br.sapiens.bellus_app.dominio.model.MarketplaceState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import javax.inject.Inject

class MarketplaceStateUpdater @Inject constructor() {

    fun update(
        currentState: ApplicationState,
        newMarketplaceState: MarketplaceState
    ): ApplicationState {
        return currentState.copy(
            marketplaceState = newMarketplaceState
        )
    }
}
