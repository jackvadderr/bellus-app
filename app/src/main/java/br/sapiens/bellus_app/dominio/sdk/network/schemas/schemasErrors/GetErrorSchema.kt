package br.sapiens.bellus_app.dominio.sdk.network.schemas.schemasErrors

import kotlinx.serialization.Serializable

@Serializable
data class GetErrorSchema(
    val detail: String,
)
