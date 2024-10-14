package br.sapiens.bellus_app.data.datasource.entity

data class GetAppointmentDTO(
    val id: String,
    val userId: String,
    val establishmentId: String,
    val serviceId: String,
    val scheduled_date: String,
    val statusRequest: String,
    val completionDate: String,
)
