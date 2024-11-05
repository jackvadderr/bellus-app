package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.utils.State

/**
 * Esta é uma interface funcional que representa um repositório para obter um usuário.
 * Possui um único método abstrato `getUserById`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface GetUserByIdRepository {

    suspend fun getById(id: String): State<UserDTO>
}