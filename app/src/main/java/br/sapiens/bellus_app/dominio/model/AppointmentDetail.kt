package br.sapiens.bellus_app.dominio.model

data class AppointmentDetail(
    val userId: String,
    val establishmentId: String,
    val serviceId: String,
    val date: String,
    val time: String,
    val statusRequest: String = "Pendente",
    val completionDate: String = "",
)