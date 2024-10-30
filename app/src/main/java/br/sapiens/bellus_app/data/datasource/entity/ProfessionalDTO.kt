package br.sapiens.bellus_app.data.datasource.entity

data class ProfessionalDTO(
    val id: String,
    val userId: String,
    val linkedEstablishmentId: String,
    val name: String,
    val profession: String,
    val cpf: String,
)