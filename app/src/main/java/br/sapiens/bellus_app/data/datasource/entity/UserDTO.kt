package br.sapiens.bellus_app.data.datasource.entity

/**
 * Objeto de Transferência de Dados (DTO) para Usuário.
 *
 * Esta classe é usada para mapear os dados do usuário da fonte de dados para o aplicativo.
 * É uma classe de dados kotlin simples com campos que podem ser nulos.
 *
 * @property name O nome do usuário. Pode ser nulo.
 * @property phone O número de telefone do usuário. Pode ser nulo.
 * @property mail O endereço de email do usuário. Pode ser nulo.
 * @property address O endereço físico do usuário. Pode ser nulo.
 * @property gender O gênero do usuário. É um Boolean onde true representa masculino e false representa feminino. Pode ser nulo.
 */
data class UserDTO(
    val username: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val mail: String? = null,
    val address: String? = null,
    val gender: Boolean? = null,
)