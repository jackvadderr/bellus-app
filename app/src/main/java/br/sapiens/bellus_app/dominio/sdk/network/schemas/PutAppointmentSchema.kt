package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class PutAppointmentSchema(
    val status_request: String,
    val completion_date: String,
)
