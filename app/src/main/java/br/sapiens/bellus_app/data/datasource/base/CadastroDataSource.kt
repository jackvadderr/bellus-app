package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.utils.State

/**
 * Esta é uma interface funcional que contém uma única função suspensa para registrar um usuário.
 * É usada como uma fonte de dados para realizar operações de registro de usuário.
 */
fun interface CadastroDataSource {

    /**
     * Esta é uma função suspensa que registra um usuário com os detalhes fornecidos.
     * Retorna um objeto State que contém o objeto UserDTO.
     * O objeto State representa o estado da operação de registro.
     *
     * @param name O nome do usuário a ser registrado.
     * @param phone O número de telefone do usuário a ser registrado.
     * @param email O endereço de email do usuário a ser registrado.
     * @param genero O gênero do usuário a ser registrado.
     * @param senha A senha do usuário a ser registrada.
     * @return Um objeto State contendo o objeto CadastroDTO.
     */
    suspend fun register(
        name: String?,
        phone: String?,
        email: String?,
        genero: GeneroEnum?,
        senha: String?
    ): State<CadastroDTO>
}