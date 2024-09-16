package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

sealed class MarketplaceAction {
    data class UpdateEstablishmentDetails(val details: List<EstablishmentDetails>) :
        MarketplaceAction()

    data class UpdateServiceDetails(val details: List<ServiceDetails>) : MarketplaceAction()
    data class SelectItem(val establishmentItemId: String) : MarketplaceAction()
}
