package br.sapiens.bellus_app.telas.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.viewmodels.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Icon
import androidx.compose.ui.res.painterResource

@Composable
fun TelaLogin(
    viewModel: LoginViewModel,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {
Text(text = "TELA DE LOGIN AMIGÃO")
}


@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {

}