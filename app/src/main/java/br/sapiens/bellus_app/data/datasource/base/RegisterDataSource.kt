package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.data.datasource.entity.UserDTO

/**
 * Esta é uma interface funcional que contém uma única função suspensa para registrar um usuário.
 * É usada como uma fonte de dados para realizar operações de registro de usuário.
 */
fun interface RegisterDataSource {

    /**
     * Esta é uma função suspensa que registra um usuário com os detalhes fornecidos.
     * Retorna um objeto State que contém o objeto UserDTO.
     * O objeto State representa o estado da operação de registro.
     *
     * @param userId O ID do usuário a ser registrado.
     * @param username O nome de usuário do usuário a ser registrado.
     * @param name O nome do usuário a ser registrado.
     * @param phone O número de telefone do usuário a ser registrado.
     * @param mail O endereço de email do usuário a ser registrado.
     * @param address O endereço do usuário a ser registrado.
     * @param gender O gênero do usuário a ser registrado.
     * @return Um objeto State contendo o objeto UserDTO.
     */
    suspend fun register(
        userId: String,
        username: String?,
        name: String?,
        phone: String?,
        mail: String?,
        address: String?,
        gender: Boolean?,
    ): State<UserDTO>
}