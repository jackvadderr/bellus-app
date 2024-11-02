package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class DeleteSchema(
    val message: String,
)
