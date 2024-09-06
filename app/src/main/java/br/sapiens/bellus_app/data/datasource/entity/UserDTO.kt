package br.sapiens.bellus_app.data.datasource.entity

/**
 * Objeto de Transferência de Dados (DTO) para Usuário.
 *
 * Esta classe é usada para mapear os dados do usuário da fonte de dados para o aplicativo.
 * É uma classe de dados kotlin simples com campos que podem ser nulos.
 */
data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val genero: String? = null,
)