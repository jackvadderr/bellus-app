package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class PostProfessionalSchema(
    val user_id: String,
    val name: String,
    val profession: String,
    val linked_establishment_id: String,
)