package br.sapiens.bellus_app.presentation.telas.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.presentation.ui.component.CustomButton
import br.sapiens.bellus_app.presentation.ui.component.GeralTextField
import br.sapiens.bellus_app.presentation.ui.component.SenhaTextField
import br.sapiens.bellus_app.presentation.viewmodels.CadastroViewModel


@Composable
fun TelaCadastro(
    viewModel: CadastroViewModel,
    navigateToBack: () -> Unit,
) {

    val nome = remember {
        mutableStateOf("")
    }

    val telefone = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val genero = remember {
        mutableStateOf("")
    }

    val senha = remember {
        mutableStateOf("")
    }

    val confirmarSenha = remember {
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
            // Espaçador inicial
            Spacer(modifier = Modifier.padding(0.dp))

            // Coluna interna com padding e alinhamento centralizado
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagem
                Image(
                    painter = painterResource(id = R.mipmap.bellus_imagem),
                    contentDescription = "Bellus Login",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                /*
                 * CAMPO DE NOME
                 */
                GeralTextField(
                    value = nome.value,
                    onValueChange = { nome.value = it },
                    placeholder = "Nome completo"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                /*
                 * CAMPO DE EMAIL
                 */
                GeralTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = "Email"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                /*
                 * CAMPO DE TELEFONE
                 */
                GeralTextField(
                    value = telefone.value,
                    onValueChange = {telefone.value = it },
                    placeholder = "Telefone"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                /*
                 * CAMPO DE SENHA
                 */
                SenhaTextField(
                    value = senha.value,
                    onValueChange = { senha.value = it },
                    placeholder = "Nova senha"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                /*
                 * CAMPO DE CONFIRMAÇÃO DE SENHA
                 */
                SenhaTextField(
                    value = confirmarSenha.value,
                    onValueChange = { confirmarSenha.value = it },
                    placeholder = "Confirme a senha"
                )

                // Selecionar gênero
                genero.value = SelecionarGenero().toString()

                /*
                 * BOTÃO PARA CADASTRAR
                 */
                CustomButton(
                    onClick = {
                        viewModel.triggerEvent(CadastroViewModel.ViewEvent.SetName(nome.value))
                        viewModel.triggerEvent(CadastroViewModel.ViewEvent.SetTelefone(telefone.value))
                        viewModel.triggerEvent(CadastroViewModel.ViewEvent.SetEmail(email.value))
                        viewModel.triggerEvent(CadastroViewModel.ViewEvent.SetGenero(genero.value))
                        if(senha.value == confirmarSenha.value) {
                            viewModel.triggerEvent(CadastroViewModel.ViewEvent.SetSenha(senha.value))
                        }
                        viewModel.triggerEvent(CadastroViewModel.ViewEvent.Event)
                    },
                    texto = "Cadastrar"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                CustomButton(
                    onClick = { navigateToBack() },
                    texto = "Voltar"
                )
            }
        }
    }
}

@Composable
fun SelecionarGenero(): GeneroEnum {
    val radioOptions = GeneroEnum.values().toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        radioOptions.forEach { opcao ->
            Row(modifier = Modifier
                .selectable(
                    selected = (opcao == selectedOption),
                    onClick = { onOptionSelected(opcao) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (opcao == selectedOption),
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        onOptionSelected(opcao)
                    },
                )
                Text(text = opcao.name, color = Color.White)
            }
        }
    }
    return selectedOption
}