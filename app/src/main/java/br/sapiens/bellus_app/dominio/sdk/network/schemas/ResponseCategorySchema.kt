package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ResponseCategorySchema(
    val id: String,
    val category: String,
    val establishment_id: String,
)

@Serializable
data class ResponseCategoryNameSchema(
    val name: String,
    val number: Int,
    val url: String,
)