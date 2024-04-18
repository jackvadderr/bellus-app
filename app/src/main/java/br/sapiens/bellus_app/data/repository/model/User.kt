package br.sapiens.bellus_app.data.repository.model

/**
 * Classe de dados que representa um Usuário na aplicação.
 *
 * @property username O nome de usuário do Usuário. Pode ser nulo.
 * @property name O nome do Usuário. Pode ser nulo.
 * @property phone O número de telefone do Usuário. Pode ser nulo.
 * @property mail O endereço de email do Usuário. Pode ser nulo.
 * @property address O endereço do Usuário. Pode ser nulo.
 * @property gender O gênero do Usuário. Pode ser nulo. Verdadeiro representa masculino, falso representa feminino.
 */
data class User(
    val username: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val mail: String? = null,
    val address: String? = null,
    val gender: Boolean? = null,
)