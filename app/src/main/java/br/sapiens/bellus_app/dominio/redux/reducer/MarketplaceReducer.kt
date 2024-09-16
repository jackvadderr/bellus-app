package br.sapiens.bellus_app.dominio.redux.reducer

import br.sapiens.bellus_app.dominio.model.MarketplaceAction
import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.model.MarketplaceState
import javax.inject.Inject

class MarketplaceReducer @Inject constructor() {

    fun reducer(
        state: MarketplaceState = MarketplaceState(),
        action: MarketplaceAction
    ): MarketplaceState {
        return when (action) {
            is MarketplaceAction.UpdateEstablishmentDetails -> state.copy(
                establishmentDetails = action.details,
                currentEvent = MarketplaceEvent.SuccessEstablishmentDetails(action.details)
            )

            is MarketplaceAction.UpdateServiceDetails -> state.copy(
                serviceDetails = action.details,
                currentEvent = MarketplaceEvent.SuccessServiceDetails(action.details)
            )

            is MarketplaceAction.SelectItem ->
                state.copy(currenteEstablishmentItemId = action.establishmentItemId)
        }
    }
}
