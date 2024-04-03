package br.sapiens.bellus_app.telas.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.ui.component.CustomButton
import br.sapiens.bellus_app.ui.component.SenhaTextField
import br.sapiens.bellus_app.ui.component.UsuarioTextField
import br.sapiens.bellus_app.viewmodels.LoginViewModel

@Composable
fun TelaLogin(
    viewModel: LoginViewModel,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {
//    val signedIn: Boolean by registerViewModel.signedIn.observeAsState(false)

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1D2B3D)), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(10.dp)
                .background(Color(0xFF1D2B3D))
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Bellus",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 40.sp,
                color = Color(0xFFFFFFFF)
            )
            Spacer(modifier = Modifier.padding(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Usuário
                UsuarioTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                )
                Spacer(modifier = Modifier.padding(8.dp))
                // Senha
                SenhaTextField(
                    value = password.value,
                    onValueChange = { password.value = it },

                )
                Spacer(modifier = Modifier.padding(10.dp))
                // Botão de login
                CustomButton(
                    onClick = {
                        navigateToHome()
                    },
                    texto = "Entrar"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                // Botão de cadastro
                CustomButton(
                    onClick = {
                        navigateToHome()
                    },
                    texto = "Cadastrar"
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Esqueceu a senha?",
                    modifier = Modifier.clickable(onClick = {
                        navigateToHome()
                    }),
                    color = Color(0xFFA0A0A0)
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {

}