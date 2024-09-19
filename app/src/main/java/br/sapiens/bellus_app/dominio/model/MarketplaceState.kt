package br.sapiens.bellus_app.dominio.model

import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

data class MarketplaceState(
    val establishmentSummaries: List<AvailableEstablishment> = emptyList(),
    val serviceDetails: List<ServiceDetails> = emptyList(),


    val currentEstablishmentItemId: String? = null,
    val currentEstablishment: EstablishmentDetail? = null,

    val reviewsCurrentEstablishment: List<ReviewsDetails>? = null,

    val currentEvent: MarketplaceEvent? = null,


    val isLoading: Boolean = false,
    val isServiceDetailsLoading: Boolean = false,
    val isEstablishmentDetailsLoading: Boolean = false,
    val isCurrentEstablishmentLoading: Boolean = false,
    val error: String? = null
)
