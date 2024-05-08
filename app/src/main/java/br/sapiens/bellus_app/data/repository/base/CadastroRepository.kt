package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.utils.State

/**
 * Esta é uma interface funcional que representa um repositório para registrar um usuário.
 * Possui um único método abstrato `register`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface CadastroRepository {

    /**
     * Esta é uma função suspensa que registra um usuário com os detalhes fornecidos.
     * @param name O nome do usuário a ser registrado.
     * @param phone O número de telefone do usuário a ser registrado.
     * @param email O endereço de email do usuário a ser registrado.
     * @param genero O gênero do usuário a ser registrado.
     * @return Um objeto State que pode conter o objeto User ou uma exceção.
     */
    suspend fun register(
        name: String?,
        phone: String?,
        email: String?,
        genero: GeneroEnum?,
        senha: String?
    ): State<Cadastro>
}