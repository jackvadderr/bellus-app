package br.sapiens.bellus_app.dominio.model.state

enum class UserProfileType {
    CLIENT,
    PROFESSIONAL,
}

data class UserProfileState(
    val currentProfileType: UserProfileType = UserProfileType.CLIENT,
    val clientInfo: ClientInfo? = null,
    val professionalInfo: ProfessionalInfo? = null,
)

data class ClientInfo(
    val id: String,
    val name: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val genero: String? = "",
)

data class ProfessionalInfo(
    val id: String,
    val userId: String? = "",
    val establishmentId: String? = "",
    val name: String? = "",
    val email: String? = "",
//    val endereco: String? = "",
)