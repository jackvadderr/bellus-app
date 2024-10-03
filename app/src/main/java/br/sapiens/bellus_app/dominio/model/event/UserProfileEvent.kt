package br.sapiens.bellus_app.dominio.model.event

import br.sapiens.bellus_app.dominio.model.state.ClientInfo
import br.sapiens.bellus_app.dominio.model.state.ProfessionalInfo

sealed class UserProfileEvent {
    data class SetClientProfile(val clientInfo: ClientInfo?) : UserProfileEvent()
    data class SetProfessionalProfile(val professionalInfo: ProfessionalInfo?) : UserProfileEvent()
}