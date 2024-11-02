package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import kotlinx.serialization.Serializable

@Serializable
data class CadastroSchema(
    val name: String,
    val phone: String,
    val genero: GeneroEnum,
    val email: String,
    val senha: String,
    val isProfessional: Boolean,
    val idade: Int,
)
