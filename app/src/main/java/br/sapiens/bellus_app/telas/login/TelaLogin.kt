package br.sapiens.bellus_app.telas.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.ui.component.CustomButton
import br.sapiens.bellus_app.ui.component.SenhaTextField
import br.sapiens.bellus_app.ui.component.UsuarioTextField
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import br.sapiens.bellus_app.viewmodels.LoginViewModel
import com.google.firebase.auth.EmailAuthProvider


@Composable
fun TelaLogin(
    viewModel: LoginViewModel,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
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
            Image(
                painter = painterResource(id = R.mipmap.bellus_imagem),
                contentDescription = "Bellus Login",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.padding(0.dp))

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
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Esqueceu a senha?",
                    modifier = Modifier.clickable(onClick = {
                        navigateToHome()
                    }),
                    color = Color(0xFFA0A0A0)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                // Botão de login
                CustomButton(onClick = {
                    val authCredential = EmailAuthProvider.getCredential(email.value, password.value)
                    viewModel.loginWithCredential(authCredential)
                }, texto = "Entrar com Google")

                Spacer(modifier = Modifier.padding(10.dp))
                // Botão de cadastro
                CustomButton(
                    onClick = {
                        navigateToHome()
                    },
                    texto = "Cadastrar"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                // Divisor personalizado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                    Text(
                        text = "Ou",
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                // Botão de cadastro
                CustomButton(
                    onClick = {
                        navigateToHome()
                    },
                    texto = "Entrar com Gogles"
                )
            }
        }
    }
    if (state.loginState == EstadoAutenticacao.AUTENTICADO) {
        navigateToHome()
    }
}