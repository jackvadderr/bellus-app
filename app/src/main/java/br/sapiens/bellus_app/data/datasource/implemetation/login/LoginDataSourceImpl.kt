package br.sapiens.bellus_app.data.datasource.implemetation.login

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.LoginDataSource
import br.sapiens.bellus_app.data.datasource.entity.AuthDTO
import br.sapiens.bellus_app.dominio.model.AuthUser
import br.sapiens.bellus_app.dominio.model.event.AuthEvent
import br.sapiens.bellus_app.dominio.redux.stores.AuthStore
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseSessionSchema
import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Implementação da interface LoginDataSource.
 *
 * Esta classe fornece a funcionalidade para fazer login de um usuário com uma credencial de autenticação fornecida.
 * Utiliza a Autenticação Firebase para o processo de login.
 */
class LoginDataSourceImpl @Inject constructor(
    private val authStore: AuthStore,
    private val provider: KtorClientProvider = KtorClientProvider(),
) : LoginDataSource {

    override suspend fun loginWithCredential(authCredential: AuthCredential): State<AuthDTO> {
        Log.d("LoginDataSourceImpl", "Starting loginWithCredential")
        return try {
            var sessionToken: String? = null

            val firebaseAuthInstance = FirebaseAuth.getInstance()
            Log.d("LoginDataSourceImpl", "FirebaseAuth instance obtained")
            val authResult = firebaseAuthInstance.signInWithCredential(authCredential).await()
            Log.d("LoginDataSourceImpl", "Auth result obtained")

            val firebaseUser = authResult.user
            val firebaseTokenJWT = firebaseUser?.getIdToken(false)?.await()?.token
            Log.d("LoginDataSourceImpl", "Firebase JWT Token: $firebaseTokenJWT")

            firebaseTokenJWT?.let { token ->
                val client = provider.client
                Log.d("LoginDataSourceImpl", "Ktor client obtained")
                val urlFinal = provider.getBaseUrl().appendPath("session/create-session")
                Log.d("LoginDataSourceImpl", "Provider FINAL URL: $urlFinal")
                val response: HttpResponse = client.post(urlFinal) {
                    setBody(Json.encodeToString(mapOf("firebase_token" to token)))
                }
                Log.d("LoginDataSourceImpl", "Response from session endpoint: ${response.status}")
                val responseSessionSchema: ResponseSessionSchema =
                    Json.decodeFromString(response.bodyAsText())
                Log.d(
                    "LoginDataSourceImpl",
                    "Response session token: ${responseSessionSchema.session_token}"
                )
                provider.setBearerTokenPrimary(responseSessionSchema.session_token)
                sessionToken = responseSessionSchema.session_token
                authStore.store.dispatch(
                    AuthEvent.UserAuthenticated(
                        AuthUser(
                            firebaseUser.uid,
                        ),
                        responseSessionSchema.session_token
                    )
                )
            }
            val userDTO = AuthDTO(
                firebaseUser?.uid,
                sessionToken
            )
            Log.d("LoginDataSourceImpl", "User authenticated successfully")
//            State.Success(firebaseAuthInstance.currentUser!!)
            State.Success(userDTO)
        } catch (exception: Exception) {
            authStore.store.dispatch(AuthEvent.AuthenticationError(exception))
            State.Error(exception)
        }
    }
}