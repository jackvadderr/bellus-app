package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ClientUserSchema(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val genero: String,
    val isProfessional: Boolean,
)

@Serializable
data class ProfissionalUserSchema(
    val id: String,
    val user: ClientUserSchema,
    val crm: String,
    val especialidade: String,
    val endereco: String,
)