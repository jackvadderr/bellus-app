package br.sapiens.bellus_app.data.datasource.entity

data class PostAppointmentDTO(
    val id: String,
    val userId: String,
    val establishmentId: String,
    val serviceId: String,
    val date: String,
    val statusRequest: String
)
