package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

sealed class MarketplaceEvent {
    object Loading : MarketplaceEvent()
    data class SuccessEstablishmentDetails(val details: List<EstablishmentDetails>) :
        MarketplaceEvent()

    data class SuccessServiceDetails(val details: List<ServiceDetails>) : MarketplaceEvent()
    data class Error(val message: String) : MarketplaceEvent()
}
