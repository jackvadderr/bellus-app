package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ClientUser(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val genero: String,
)

@Serializable
data class ProfissionalUser(
    val id: String,
    val user: ClientUser,
    val crm: String,
    val especialidade: String,
    val endereco: String,
)