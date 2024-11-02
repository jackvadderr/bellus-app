package br.sapiens.bellus_app.data.datasource.implemetation.login

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.CadastroDataSource
import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCadastroSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toCadastroDTO
import br.sapiens.bellus_app.utils.toCadastroSchema
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Implementação da interface RegisterDataSource.
 *
 * @property firebaseFirestore A instância do FirebaseFirestore a ser usada para operações de banco de dados.
 */
class CadastroDataSourceImpl @Inject constructor(
//    private val firebaseAuth: FirebaseAuth,
//    private val firebaseFirestore: FirebaseFirestore
    private val provider: KtorClientProvider,
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
//    override suspend fun register(
//        name: String?,
//        phone: String?,
//        email: String?,
//        genero: GeneroEnum?,
//        senha: String?
//    ): State<CadastroDTO> {
//        return try {
//            /*
//             * Aqui nós vamos cadastrar no Firebase Auth
//             * utilizando apenas o email e senha
//             */
//            Log.d("CadastroDataSourceImpl", "Iniciando registro de usuário")
//            val result =
//                firebaseAuth.createUserWithEmailAndPassword(email ?: "", senha ?: "").await()
//            val usuarioCriadoUUID = result.user?.uid
//            Log.d("CadastroDataSourceImpl", "UUID do usuário criado: $usuarioCriadoUUID")
//            if (result != null) {
//                Log.d("CadastroDataSourceImpl", "Usuário registrado com sucesso")
//            }
//            if (result.user != null) {
//                val user = CadastroDTO(
//                    name = name,
//                    phone = phone,
//                    genero = genero,
//                    email = email,
//                    isProfessional = false,
//                    idade = 18,
//                )
//                /*
//                 * Caso o cadastro dê certo,
//                 * agora nós vamos criar o respectivo usuário no banco de dados
//                 * para criar um match com o usuário do Firebase Auth e o usuário do Firestore
//                 * utilizando o o UUID do Firebase Auth
//                 */
//                firebaseFirestore
//                    .collection("CLIENTES")
//                    .document("$usuarioCriadoUUID")
//                    .set(user)
//                    .await()
//                State.Success(user)
//            } else {
//                throw UserNotFoundException("Usuário não encontrado")
//            }
//        } catch (exception: Exception) {
//            Log.e("CadastroDataSourceImpl", "Erro ao registrar usuário", exception)
//            State.Error(exception)
//        }
//    }

    override suspend fun register(
        name: String?,
        phone: String?,
        email: String?,
        genero: GeneroEnum?,
        senha: String?
    ): State<CadastroDTO> {
        return try {
            Log.d("CadastroDataSourceImpl", "Iniciando registro de usuário")

            val user = CadastroDTO(
                name = name,
                phone = phone,
                genero = genero,
                email = email,
                isProfessional = false,
                idade = 18,
            )
            Log.d("CadastroDataSourceImpl", "CadastroDTO criado: $user")

            val schema = user.toCadastroSchema(senha = senha.toString())
            Log.d("CadastroDataSourceImpl", "CadastroSchema criado: $schema")

            val url = provider.getBaseUrl().appendPath("users/")
            Log.d("CadastroDataSourceImpl", "URL de requisição: $url")

            val client = provider.client
            val response = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(schema)
            }
            Log.d("CadastroDataSourceImpl", "Resposta recebida: ${response.status}")

            val responseBody = response.bodyAsText()
            Log.d("CadastroDataSourceImpl", "Corpo da resposta: $responseBody")

            val cadastroResponse: ResponseCadastroSchema =
                Json.decodeFromString(responseBody)
            Log.d(
                "CadastroDataSourceImpl",
                "ResponseCadastroSchema decodificado: $cadastroResponse"
            )

            val newCadastroDTO = cadastroResponse.toCadastroDTO()
            Log.d("CadastroDataSourceImpl", "Novo CadastroDTO: $newCadastroDTO")

            State.Success(newCadastroDTO)
        } catch (exception: Exception) {
            Log.e("CadastroDataSourceImpl", "Erro ao registrar usuário", exception)
            State.Error(exception)
        }
    }
}