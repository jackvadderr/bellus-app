package br.sapiens.bellus_app.data.datasource.implemetation

import br.sapiens.bellus_app.utils.Contantes.Firestore.USERS
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.UserNotFoundException
import com.google.firebase.firestore.FirebaseFirestore
import br.sapiens.bellus_app.data.datasource.base.GetUserDataSource
import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementação da interface GetUserDataSource.
 *
 * @property firebaseFirestore A instância do FirebaseFirestore a ser usada para operações de banco de dados.
 */
class GetUserDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : GetUserDataSource {

    /**
     * Busca um usuário no banco de dados Firestore pelo seu ID de usuário.
     *
     * @param userId O ID do usuário a ser buscado.
     * @return Um objeto State contendo o objeto UserDTO buscado ou um erro.
     */
    override suspend fun getUserById(userId: String): State<UserDTO> {
        return try {
            val userRef = firebaseFirestore.collection(USERS).document(userId).get().await()
            val data = userRef.toObject(UserDTO::class.java)
            if (data != null)
                State.Success(data)
            else
                State.Error(UserNotFoundException("Usuário não encontrado!"))

        } catch (exception: Exception) {
            // Se ocorrer uma exceção durante a operação, retorne-a envolvida em um objeto State de erro
            State.Error(exception)
        }
    }
}