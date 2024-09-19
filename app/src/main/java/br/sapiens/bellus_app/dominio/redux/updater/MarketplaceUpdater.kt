package br.sapiens.bellus_app.dominio.redux.updater

import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.model.MarketplaceState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import javax.inject.Inject

class MarketplaceStateUpdater @Inject constructor() {

    fun update(
        event: MarketplaceEvent,
        currentState: ApplicationState,
    ): ApplicationState {
        val newMarketplaceState: MarketplaceState = when (event) {
            is MarketplaceEvent.SuccessGetEstablishmentSummary -> {
                currentState.marketplaceState.copy(
                    establishmentSummaries = event.details
                )
            }

            is MarketplaceEvent.SuccessGetEstablishmentCurrent -> {
                currentState.marketplaceState.copy(
                    currentEstablishment = event.details
                )
            }

            is MarketplaceEvent.SuccessGetReviews -> {
                currentState.marketplaceState.copy(
                    reviewsCurrentEstablishment = event.details
                )
            }

            is MarketplaceEvent.Error -> {
                currentState.marketplaceState.copy(
                    error = event.message
                )
            }

            is MarketplaceEvent.Loading -> {
                currentState.marketplaceState.copy(
                    isLoading = true
                )
            }

            is MarketplaceEvent.SuccessServiceDetails -> {
                currentState.marketplaceState.copy(
                    serviceDetails = event.details
                )
            }
        }
        return currentState.copy(
            marketplaceState = newMarketplaceState
        )
    }
}
