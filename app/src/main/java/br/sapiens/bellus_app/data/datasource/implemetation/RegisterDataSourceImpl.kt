package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import br.sapiens.bellus_app.data.datasource.base.RegisterDataSource
import br.sapiens.bellus_app.utils.Contantes.Firestore.USERS
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.utils.UserNotFoundException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementação da interface RegisterDataSource.
 *
 * @property firebaseFirestore A instância do FirebaseFirestore a ser usada para operações de banco de dados.
 */
class RegisterDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : RegisterDataSource {

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
    ): State<UserDTO> {
        return try {
            val user = UserDTO(
                username = username,
                name = name,
                phone = phone,
                mail = mail,
                address = address,
                gender = gender,
            )
            // Estamos criando um novo objeto UserDTO com os dados passado na função
            // e acessando o Firestore para salvar esse objeto no banco de dados
            firebaseFirestore.collection(USERS).document(userId).set(user).await()
            val userRef = firebaseFirestore.collection(USERS).document(userId).get().await()

            // Estamos acessando o Firestore para pegar o objeto UserDTO que acabamos de salvar
            // e retornando esse objeto no State.Success
            val data = userRef.toObject(UserDTO::class.java)
            if (data != null)
                State.Success(data)
            else
                State.Error(UserNotFoundException("Usuário não encontrado!"))

        } catch (exception: Exception) {
            Log.e("RegisterDataSourceImpl", "Erro ao registrar usuário", exception)
            State.Error(exception)
        }
    }
}