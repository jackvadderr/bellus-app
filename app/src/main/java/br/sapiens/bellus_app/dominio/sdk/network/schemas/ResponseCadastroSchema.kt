package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCadastroSchema(
    val id: String,
    val name: String,
    val email: String,
    val isEmailVerified: Boolean,
    val isProfessional: Boolean,
    val phone: String,
    val genero: GeneroEnum,
    val providerId: String,
    val idade: Int,
)
