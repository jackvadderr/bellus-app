package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ClientUser

/**
 * Esta é uma interface funcional que contém uma única função suspensa para buscar um usuário pelo seu ID.
 * É usada como uma fonte de dados para recuperar dados do usuário.
 */
fun interface GetUserDataSource {

    /**
     * Esta é uma função suspensa que busca um usuário pelo seu ID.
     * Retorna um objeto State que contém o objeto UserDTO.
     * O objeto State representa o estado da operação de busca de dados.
     *
     * @return Um objeto State contendo o objeto UserDTO.
     */
    suspend fun getUserById(): State<UserDTO>
}