package br.sapiens.bellus_app.dominio.model.state

enum class UserProfileType {
    CLIENT,
    PROFESSIONAL,
}

data class UserProfileState(
    val profileType: UserProfileType = UserProfileType.CLIENT,
    val clientInfo: ClientInfo? = null,
    val professionalInfo: ProfessionalInfo? = null,
)

data class ClientInfo(
    val id: String,
    val name: String? = "",
    val email: String? = "",
)

data class ProfessionalInfo(
    val id: String,
    val name: String,
)