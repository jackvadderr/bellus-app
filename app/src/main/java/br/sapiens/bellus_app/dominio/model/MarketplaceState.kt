package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

data class MarketplaceState(
    val establishmentDetails: List<EstablishmentDetails> = emptyList(),
    val serviceDetails: List<ServiceDetails> = emptyList(),
    val currentEvent: MarketplaceEvent? = null,
    val currenteEstablishmentItemId: String? = null,
)
