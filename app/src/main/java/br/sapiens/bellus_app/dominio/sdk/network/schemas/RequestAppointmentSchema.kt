package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class RequestAppointmentSchema(
    val user_id: String,
    val establishment_id: String,
    val service_id: String,
    val scheduled_date: String,
    val status_request: String,
    val completion_date: String,
)