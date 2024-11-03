package br.sapiens.bellus_app.dominio.model.event

import br.sapiens.bellus_app.dominio.model.AppointmentDetail
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails


sealed class MarketplaceEvent {
    data object Loading : MarketplaceEvent()
    data class SuccessGetCurrentAppointmentId(val id: String) : MarketplaceEvent()
    data class SuccessGetEstablishmentSummary(val details: List<AvailableEstablishment>) :
        MarketplaceEvent()

    data class SuccessGetEstablishmentCurrent(val details: EstablishmentDetail) :
        MarketplaceEvent()

    data class SuccessCreateService(val createService: ServiceDetails) : MarketplaceEvent()

    data class SuccessUpdateService(val updateService: ServiceDetails) : MarketplaceEvent()

    data class SuccessServiceDetails(val details: List<ServiceDetails>) : MarketplaceEvent()

    data class SucessGetCurrentService(val details: ServiceDetails) : MarketplaceEvent()

    data class CreateAppointment(val appointment: AppointmentDetail) : MarketplaceEvent()

    data class SuccessGetReviews(val details: List<ReviewsDetails>) : MarketplaceEvent()

    data class Error(val message: String) : MarketplaceEvent()
}
