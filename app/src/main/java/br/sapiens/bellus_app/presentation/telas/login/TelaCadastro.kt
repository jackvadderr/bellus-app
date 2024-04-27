package br.sapiens.bellus_app.presentation.telas.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.GeralTextField
import br.sapiens.bellus_app.presentation.ui.component.SenhaTextField
import br.sapiens.bellus_app.presentation.ui.component.UsuarioTextField
import br.sapiens.bellus_app.presentation.viewmodels.CadastroViewModel


@Composable
fun TelaCadastro(
    viewModel: CadastroViewModel,
    navigateToBack: () -> Unit,
) {
    val email = remember {
        mutableStateOf("")
    }

    val nome = remember {
        mutableStateOf("")
    }

    val telefone = remember {
        mutableStateOf("")
    }

    val senha = remember {
        mutableStateOf("")
    }

    val confirmSenha = remember {
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
            Spacer(modifier = Modifier.padding(0.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*
                 * Campos de texto para o email
                 */
                UsuarioTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = "Email"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                /*
                 * Campo de texto para o nome
                 */
                GeralTextField(
                    value = nome.value,
                    onValueChange = { nome.value = it },
                    placeholder = "Nome completo"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                /*
                 * Campo de texto para o telefone
                 */
                GeralTextField(
                    value = telefone.value,
                    onValueChange = { telefone.value = it },
                    placeholder = "Telefone"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                /*
                 * Campo de texto para a senha
                 */
                SenhaTextField(
                    value = senha.value,
                    onValueChange = { senha.value = it },
                )
                Spacer(modifier = Modifier.padding(8.dp))
                SenhaTextField(
                    value = confirmSenha.value,
                    onValueChange = { confirmSenha.value = it },
                    placeholder = "Confirmar senha"
                )
                /*
                 * Botão para cadastar
                 */
//                CustomButton(
//                    onClick = {
//
//                    },
//                    texto = "Cadastrar"
//                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewTelaCadastro() {
    TelaCadastro(
        viewModel = CadastroViewModel(),
        navigateToBack = {}
    )
}