package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.data.repository.model.User

/**
 * Esta é uma interface funcional que representa um repositório para obter um usuário.
 * Possui um único método abstrato `getUserById`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface GetUserRepository {

    /**
     * Esta é uma função suspensa que obtém um usuário pelo seu ID.
     * @param userId O ID do usuário a ser recuperado.
     * @return Um objeto State que pode conter o objeto User ou uma exceção.
     */
    suspend fun getUserById(userId: String): State<User>
}