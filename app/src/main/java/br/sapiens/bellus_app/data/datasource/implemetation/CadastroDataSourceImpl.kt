package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.UserNotFoundException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementação da interface RegisterDataSource.
 *
 * @property firebaseFirestore A instância do FirebaseFirestore a ser usada para operações de banco de dados.
 */
class CadastroDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CadastroDataSource {

    /**
     * Registra um novo usuário no Firebase Auth e o combina com banco de dados Firestore.
     *
     * @param name O nome do usuário.
     * @param phone O número de telefone do usuário.
     * @param email O endereço de email do usuário.
     * @param genero O gênero do usuário.
     *
     * @return Um objeto State que representa o resultado da operação de registro.
     *         Se a operação for bem-sucedida, o objeto State conterá o objeto CadastroDTO registrado.
     *         Se a operação falhar, o objeto State conterá a exceção que causou a falha.
     */
    override suspend fun register(
        name: String?,
        phone: String?,
        email: String?,
        genero: GeneroEnum?,
        senha: String?
    ): State<CadastroDTO> {
        return try {
            /*
             * Aqui nós vamos cadastrar no Firebase Auth
             * utilizando apenas o email e senha
             */
            val result = firebaseAuth.createUserWithEmailAndPassword(email?: "", senha?: "").await()
            val usuarioCriadoUUID = result.user?.uid
            if(result != null) {
                Log.d("RegisterDataSourceImpl", "Usuário registrado com sucesso")
            }
            if(result.user != null) {
                val user = CadastroDTO(
                    name = name,
                    phone = phone,
                    genero = genero,
                    email = email,
                )
                /*
                 * Caso o cadastro dê certo,
                 * agora nós vamos criar o respectivo usuário no banco de dados
                 * para criar um match com o usuário do Firebase Auth e o usuário do Firestore
                 * utilizando o o UUID do Firebase Auth
                 */
                firebaseFirestore
                    .collection("CLIENTES")
                    .document("$usuarioCriadoUUID")
                    .set(user)
                    .await()
                State.Success(user)
            } else {
                throw UserNotFoundException("Usuário não encontrado")
            }
        } catch (exception: Exception) {
            Log.e("RegisterDataSourceImpl", "Erro ao registrar usuário", exception)
            State.Error(exception)
        }
    }
}