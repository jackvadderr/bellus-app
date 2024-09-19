package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

sealed class MarketplaceAction {
    data class UpdateEstablishmentAvailables(val details: List<AvailableEstablishment>) :
        MarketplaceAction()

    data class UpdateEstablishmentCurrent(val details: EstablishmentDetail) :
        MarketplaceAction()

    data class UpdateServiceDetails(val details: List<ServiceDetails>) : MarketplaceAction()

    // TODO: Implement SelectItem in ServiceSelection screen
    data class SelectItem(val establishmentItemId: String) : MarketplaceAction()
}
