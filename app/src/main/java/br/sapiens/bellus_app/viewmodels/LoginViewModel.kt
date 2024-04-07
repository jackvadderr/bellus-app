package br.sapiens.bellus_app.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.sapiens.bellus_app.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState

class LoginViewModel : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun signInWithGoogle(context: android.content.Context) {
        val oneTapClient = Identity.getSignInClient(context)
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId((R.string.default_web_client_id).toString())
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                // Handle sign-in success
                _authState.value = AuthState.Success(result)
                Log.d("LoginViewModel", "signInWithGoogle: success")
            }
            .addOnFailureListener { exception ->
                // Handle sign-in failure
                _authState.value = AuthState.Error(exception)
                Log.d("LoginViewModel", "signInWithGoogle: failure")
            }
    }

    sealed class AuthState {
        data class Success(val result: BeginSignInResult): AuthState()
        data class Error(val exception: Exception): AuthState()
    }
}




