package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSessionSchema(
    val session_token: String,
    val expires_at: String
)