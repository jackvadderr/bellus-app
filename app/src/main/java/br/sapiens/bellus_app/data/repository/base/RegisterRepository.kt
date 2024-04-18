package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.data.repository.model.User

/**
 * Esta é uma interface funcional que representa um repositório para registrar um usuário.
 * Possui um único método abstrato `register`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface RegisterRepository {

    /**
     * Esta é uma função suspensa que registra um usuário com os detalhes fornecidos.
     * @param userId O ID do usuário a ser registrado.
     * @param username O nome de usuário do usuário a ser registrado.
     * @param name O nome do usuário a ser registrado.
     * @param phone O número de telefone do usuário a ser registrado.
     * @param mail O endereço de email do usuário a ser registrado.
     * @param address O endereço do usuário a ser registrado.
     * @param gender O gênero do usuário a ser registrado.
     * @return Um objeto State que pode conter o objeto User ou uma exceção.
     */
    suspend fun register(
        userId: String,
        username: String?,
        name: String?,
        phone: String?,
        mail: String?,
        address: String?,
        gender: Boolean?,
    ): State<User>
}