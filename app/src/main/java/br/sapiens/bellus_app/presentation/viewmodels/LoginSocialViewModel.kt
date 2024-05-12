package br.sapiens.bellus_app.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.usecase.LoginUseCase
//import br.sapiens.bellus_app.utils.FornecedorCredenciais.WEB_GOOGLE
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import dagger.hilt.android.lifecycle.HiltViewModel
//import de.cidaas.sdk.android.Cidaas
//import de.cidaas.sdk.android.helper.enums.EventResult
//import de.cidaas.sdk.android.helper.extension.WebAuthError
//import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginSocialViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
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


//    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//    suspend fun googleSign(context: Context) {
//        try {
//            val credentialManager = CredentialManager.create(context)
//            Log.d("GoogleSign","Debug 1")
//
//            val rawNonce = UUID.randomUUID().toString()
//            val bytes = rawNonce.toByteArray()
//            val md  = MessageDigest.getInstance("SHA-256")
//            val digest = md.digest(bytes)
//            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
//
//            val googleIdOptions: GetGoogleIdOption = GetGoogleIdOption.Builder()
//                .setFilterByAuthorizedAccounts(false)
//                .setServerClientId("410851760544-cvjmilt9hvqcgd5on3tc919j60779qfs.apps.googleusercontent.com")
////                .setNonce(hashedNonce)
//                .build()
//            Log.d("GoogleSign","Debug 2")
//
//            val request: GetCredentialRequest = GetCredentialRequest.Builder()
//                .addCredentialOption(googleIdOptions)
//                .build()
//            Log.d("GoogleSign","Debug 3")
//
//            val result = credentialManager.getCredential(
//                request = request,
//                context = context,
//            )
//            Log.d("GoogleSign","Debug 4")
//            val credential = result.credential
//            Log.d("GoogleSign","Debug 5")
//
//            val googleIdTokenCredential = GoogleIdTokenCredential
//                .createFrom(credential.data)
//            Log.d("GoogleSign","Debug 6")
//
//            val googleIdToken = googleIdTokenCredential.idToken
//            Log.d("GoogleSign","Debug 7")
//            Log.i("TAG", googleIdToken)
//
//            Log.d("TAG", "VOCÊ ESTÁ LOGADO")
//
//
//            val providers = arrayListOf(
//                AuthUI.IdpConfig.GoogleBuilder().build()
//            )
//
//            val signInIntent = AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build()
//
//            context.startActivity(signInIntent)
//            Log.d("LoginSocialViewModel", "Você está autenticado ${triggerEvent(ViewEvent.SetState(EstadoAutenticacao.AUTENTICADO))}")
//        } catch (e: Exception) {
//            Log.d("Exception", Log.getStackTraceString(e))
//            triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
//        }

//    fun googleSign(context: Context) {
//        val cidaas: Cidaas = Cidaas(context)
//        cidaas.loginWithSocial(
//            context,
//        "requestID",
//                WEB_GOOGLE,
//            "Nullable_Color_Parameter_In_Color_Code",
//            object : EventResult<AccessTokenEntity> {
//                override fun success(result: AccessTokenEntity) {
//                    // Success Result
//                    triggerEvent(ViewEvent.SetState(EstadoAutenticacao.AUTENTICADO))
//                }
//
//                override fun failure(error: WebAuthError) {
//                    // Failure Result
//                    triggerEvent(ViewEvent.SetState(EstadoAutenticacao.NAO_AUTENTICADO))
//                }
//            })
//    }
}