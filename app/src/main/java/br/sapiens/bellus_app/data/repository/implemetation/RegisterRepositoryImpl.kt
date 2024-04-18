package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.RegisterDataSource
import br.sapiens.bellus_app.data.repository.base.RegisterRepository
import br.sapiens.bellus_app.data.repository.model.User
import br.sapiens.bellus_app.data.repository.model.mapModel
import br.sapiens.bellus_app.utils.State

import javax.inject.Inject

/**
 * Implementação da interface RegisterDataSource.
 *
 * @property firebaseFirestore A instância do FirebaseFirestore a ser usada para operações de banco de dados.
 */
class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource,
) : RegisterRepository {

    /**
     * Registra um novo usuário no banco de dados Firestore.
     *
     * @param userId O identificador único do usuário.
     * @param username O nome de usuário do usuário.
     * @param name O nome do usuário.
     * @param phone O número de telefone do usuário.
     * @param mail O endereço de email do usuário.
     * @param address O endereço físico do usuário.
     * @param gender O gênero do usuário.
     *
     * @return Um objeto State que representa o resultado da operação de registro.
     *         Se a operação for bem-sucedida, o objeto State conterá o objeto UserDTO registrado.
     *         Se a operação falhar, o objeto State conterá a exceção que causou a falha.
     */
    override suspend fun register(
        userId: String,
        username: String?,
        name: String?,
        phone: String?,
        mail: String?,
        address: String?,
        gender: Boolean?,
    ): State<User> {
        return try {
            when (val response = registerDataSource.register(
                userId = userId,
                username = username,
                name = name,
                phone = phone,
                mail = mail,
                address = address,
                gender = gender
            )) {
                is State.Success -> State.Success(response.data.mapModel())
                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}