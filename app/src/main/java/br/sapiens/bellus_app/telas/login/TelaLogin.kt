package br.sapiens.bellus_app.telas.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

@Composable
fun TelaLogin(navController: NavHostController) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response = IdpResponse.fromResultIntent(result.data)
            if (response?.error == null) {
                // Autenticação bem-sucedida, navegue para a tela inicial
                // Removido navigateToHome()
            } else {
                // Trate o erro de autenticação
            }
        }
    }

    Button(onClick = {
        // Inicie o processo de autenticação do FirebaseUI Auth
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            // Adicione outros provedores conforme necessário
        )
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        launcher.launch(intent)
    }) {
        Text("Sign in with FirebaseUI Auth")
    }
}