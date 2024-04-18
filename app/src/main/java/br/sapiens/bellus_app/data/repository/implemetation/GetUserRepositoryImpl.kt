package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.repository.base.GetUserRepository
import br.sapiens.bellus_app.data.repository.model.User
import br.sapiens.bellus_app.data.repository.model.mapModel
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

/**
 * Esta classe é uma implementação da interface GetUserRepository.
 * Ela usa um GetUserDataSource para buscar os dados do usuário.
 * A classe é injetada com a instância de GetUserDataSource.
 *
 * @property getUserDataSource A fonte de dados usada para buscar os dados do usuário.
 */
class GetUserRepositoryImpl @Inject constructor(
    private val getUserDataSource: GetUserDataSource,
) : GetUserRepository {

    /**
     * Esta função busca um usuário pelo seu ID.
     * Ela usa o GetUserDataSource para buscar os dados do usuário e mapeia os dados para o modelo User.
     * Se ocorrer um erro durante a busca, ela retorna um State.Error com a exceção.
     *
     * @param userId O ID do usuário a ser buscado.
     * @return Um objeto State que pode conter o objeto User ou uma exceção.
     */
    override suspend fun getUserById(userId: String): State<User> {
        return try {
            // Chama a função getUserById do getUserDataSource com o userId fornecido
            // A resposta é armazenada na variável 'response'
            when (val response = getUserDataSource.getUserById(userId = userId)) {
                // Se a resposta for um sucesso (ou seja, os dados do usuário foram obtidos com sucesso)
                // Mapeia os dados da resposta para o modelo User e retorna um State.Success com os dados do usuário
                is State.Success -> State.Success(response.data.mapModel())
                // Se a resposta for um erro (ou seja, ocorreu um erro ao obter os dados do usuário)
                // Retorna o State.Error como está
                is State.Error -> response
            }
        // Se ocorrer uma exceção ao tentar obter os dados do usuário
        // Retorna um State.Error com a exceção
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}