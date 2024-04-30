package br.sapiens.bellus_app.presentation.viewmodels

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import com.firebase.ui.auth.AuthUI
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class LoginSocialViewModel @Inject constructor(
//    private val loginUseCase: LoginUseCase,
): BaseViewModel<LoginSocialViewModel.ViewState, LoginSocialViewModel.ViewEvent>() {

    override fun createInitialState(): ViewState = ViewState()

    override fun triggerEvent(event: ViewEvent) {
        viewModelScope.launch {
            when (event) {
                is ViewEvent.SetState -> {
                    setState {
                        state.copy(
                            isLoading = false,
                            loginState = event.state
                        )
                    }
                }
                is ViewEvent.SetLoading -> {
                    setState {
                        state.copy(
                            isLoading = event.state
                        )
                    }
                }
            }
        }
    }

    sealed class ViewEvent : IViewEvent {
        class SetLoading(val state: Boolean) : ViewEvent()
        class SetState(val state: EstadoAutenticacao) : ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val loginState: EstadoAutenticacao? = null,
    ) : IViewState

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun googleSign(context: Context) {
        try {
            val credentialManager = CredentialManager.create(context)

            val rawNonce = UUID.randomUUID().toString()
            val bytes = rawNonce.toByteArray()
            val md  = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

            val googleIdOptions: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(R.string.default_web_client_id.toString())
                .setNonce(hashedNonce)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOptions)
                .build()


            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            val credential = result.credential

            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            Log.i("TAG", googleIdToken)

            Log.d("TAG", "VOCÊ ESTÁ LOGADO")


            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            context.startActivity(signInIntent)
            Log.d("LoginSocialViewModel", "Você está autenticado ${triggerEvent(ViewEvent.SetState(EstadoAutenticacao.AUTENTICADO))}")
        } catch (e: GetCredentialException) {
            // Handle the GetCredentialCancellationException here
            Log.d("GetCredentialException", Log.getStackTraceString(e))
            triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
        } catch (e: GoogleIdTokenParsingException) {
            Log.d("GoogleIdTokenParsingException", Log.getStackTraceString(e))
            triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
        } catch (e: NoCredentialException) {
            Log.d("NoCredentialException", Log.getStackTraceString(e))
            triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
        } catch (e: GetCredentialCancellationException) {
            Log.d("GetCredentialCancellationException", Log.getStackTraceString(e))
            triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
        }
    }
}