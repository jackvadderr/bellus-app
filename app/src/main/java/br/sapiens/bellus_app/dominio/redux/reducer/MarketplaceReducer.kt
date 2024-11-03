package br.sapiens.bellus_app.dominio.redux.reducer

import br.sapiens.bellus_app.dominio.model.event.MarketplaceEvent
import br.sapiens.bellus_app.dominio.model.state.MarketplaceState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.stores.IStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketplaceReducer @Inject constructor() {

    fun reducer(store: IStore<ApplicationState>): Flow<ApplicationState> {
        return store.stateFlow.map { currentState ->
            val newState: MarketplaceState =
                when (val event: MarketplaceEvent? = store.getLastAction() as? MarketplaceEvent) {
                    is MarketplaceEvent.SuccessGetEstablishmentSummary -> {
                        currentState.marketplaceState.copy(
                            establishmentSummaries = event.details,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.SuccessGetEstablishmentCurrent -> {
                        currentState.marketplaceState.copy(
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.SuccessGetReviews -> {
                        currentState.marketplaceState.copy(
                            currentEvent = event,
                            reviewsCurrentEstablishment = event.details
                        )
                    }

                    is MarketplaceEvent.SuccessServiceDetails -> {
                        currentState.marketplaceState.copy(
                            serviceDetails = event.details,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.CreateAppointment -> {
                        currentState.marketplaceState.copy(
                            currentAppointment = event.appointment,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.SucessGetCurrentService -> {
                        currentState.marketplaceState.copy(
                            currentServiceDetails = event.details,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.Error -> currentState.marketplaceState.copy(currentEvent = event)
                    MarketplaceEvent.Loading -> currentState.marketplaceState.copy(currentEvent = event)

                    is MarketplaceEvent.SuccessCreateService -> {
                        currentState.marketplaceState.copy(
                            createService = event.createService,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.SuccessUpdateService -> {
                        currentState.marketplaceState.copy(
                            updateService = event.updateService,
                            currentEvent = event
                        )
                    }

                    is MarketplaceEvent.SuccessGetCurrentAppointmentId -> {
                        currentState.marketplaceState.copy(
                            currentAppointmentId = event.id
                        )
                    }

                    null -> currentState.marketplaceState

                }
            ApplicationState(marketplaceState = newState)
        }
    }
}
