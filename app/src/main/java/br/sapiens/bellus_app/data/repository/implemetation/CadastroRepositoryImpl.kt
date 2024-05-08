package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.data.repository.model.mapModel
import br.sapiens.bellus_app.utils.State

import javax.inject.Inject

/**
 * Implementação da interface RegisterDataSource.
 *
 */
class CadastroRepositoryImpl @Inject constructor(
    private val cadastroDataSource: CadastroDataSource,
) : CadastroRepository {

    /**
     * Registra um novo usuário no banco de dados Firestore.
     *
     * @param name O nome do usuário.
     * @param phone O número de telefone do usuário.
     * @param email O endereço de email do usuário.
     * @param genero O gênero do usuário.
     *
     * @return Um objeto State que representa o resultado da operação de registro.
     *         Se a operação for bem-sucedida, o objeto State conterá o objeto UserDTO registrado.
     *         Se a operação falhar, o objeto State conterá a exceção que causou a falha.
     */
    override suspend fun register(
        name: String?,
        phone: String?,
        email: String?,
        genero: GeneroEnum?,
        senha: String?
    ): State<Cadastro> {
        return try {
            when (val response = cadastroDataSource.register(
                name = name,
                phone = phone,
                genero = genero,
                email = email,
                senha = senha,
            )) {
                is State.Success -> State.Success(response.data.mapModel())
                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}