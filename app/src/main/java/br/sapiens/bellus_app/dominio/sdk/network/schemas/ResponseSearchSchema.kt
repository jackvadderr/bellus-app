package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchSchema(
    val establishment_id: String,
)
