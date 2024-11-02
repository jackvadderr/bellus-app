package br.sapiens.bellus_app.presentation.telas.professionalSide

import CustomButton
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.EstadoEnum
import br.sapiens.bellus_app.presentation.ui.component.CustomOutlinedTextField
import br.sapiens.bellus_app.presentation.ui.component.GeralTextField
import br.sapiens.bellus_app.presentation.viewmodels.ParceiroCadastroViewModel


@Composable
fun TelaCadastroParceiro(
    viewModel: ParceiroCadastroViewModel,
    navigateToSplash: () -> Unit,
) {

    val viewState by viewModel.uiState.collectAsState()
    val context = viewModel.context

    var bairro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var razaoSocial by remember { mutableStateOf("") }
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var profissional_profission by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf(EstadoEnum.AC) }
    var cep by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Vamos utilizar as informações prêvias do cadastro do cliente realizado para a criação do profissional")
        }
        item {
            Text("Preencha os campos abaixo para concluir o cadastro do estabelecimento")
        }
        item {
            Text("Dados do Profissional")
        }
        item {
            GeralTextField(
                placeholder = "CPF",
                value = cpf,
                onValueChange = { cpf = it })
        }
        item {
            CustomOutlinedTextField(
                selectedSpecialty = profissional_profission,
                onSpecialtySelected = { profissional_profission = it },
                options = listOf("Barbeiro", "Salão de beleza", "Depilação"),
                placeholder = "Profissão",
            )
        }
        item {
            Text("Dados do Estabelecimento")
        }


        item {
            GeralTextField(
                placeholder = "CNPJ (Obrigatório)",
                value = cnpj,
                onValueChange = { cnpj = it },
//                keyboardType = KeyboardType.Number
            )
        }
        item {
            GeralTextField(
                placeholder = "Razão Social",
                value = razaoSocial,
                onValueChange = { razaoSocial = it })
        }
        item {
            GeralTextField(
                placeholder = "Nome do Estabelecimento",
                value = nomeEstabelecimento,
                onValueChange = { nomeEstabelecimento = it })
        }
        item {
            GeralTextField(
                placeholder = "Celular ou Telefone do Estabelecimento",
                value = telefone,
                onValueChange = { telefone = it },
//                keyboardType = KeyboardType.Phone
            )
        }
        item {
            Text("Dados do endereço")
        }
        item {
            GeralTextField(
                placeholder = "Cep",
                value = cep,
                onValueChange = { cep = it })
        }
        item {
            GeralTextField(
                placeholder = "Rua",
                value = rua,
                onValueChange = { rua = it })
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                GeralTextField(
                    placeholder = "Número",
                    value = numero,
                    onValueChange = { numero = it },
                )
                Spacer(modifier = Modifier.width(8.dp))
                GeralTextField(
                    placeholder = "Complemento",
                    value = complemento,
                    onValueChange = { complemento = it },
                )
            }
        }
        item {
            GeralTextField(
                placeholder = "Bairro",
                value = bairro,
                onValueChange = { bairro = it })
        }
        item {
            GeralTextField(
                placeholder = "Cidade",
                value = cidade,
                onValueChange = { cidade = it })
        }
        item {
            EstadoDropdown(
                selectedEstado = estado,
                onEstadoSelected = { estado = it }
            )
        }
        item {
//            SubmitButton(onClick = {
//                viewModel.triggerEvent(
//                    ParceiroCadastroViewModel.ViewEvent.Submit(
//                        bairro,
//                        endereco,
//                        numero,
//                        complemento,
//                        cnpj,
//                        razaoSocial,
//                        nomeEstabelecimento,
//                        telefone,
//                        especialidade
//                    )
//                )
//            })
            CustomButton(onClick = {
                viewModel.triggerEvent(
                    ParceiroCadastroViewModel.ViewEvent.Submit(
                        bairro = bairro,
                        rua = rua,
                        numero = numero,
                        complemento = complemento,
                        cnpj = cnpj,
                        razaoSocial = razaoSocial,
                        nomeEstabelecimento = nomeEstabelecimento,
                        telefone = telefone,
                        profissional_profission = profissional_profission,
                        cidade = cidade,
                        estado = estado,
                        cep = cep,
                        cpf = cpf,
                    )
                )
                viewModel.criarEstabelecimentoEProfissional()
//                when (viewState) {
//                    ParceiroCadastroViewModel.ViewState.Loading -> {}
//                    ParceiroCadastroViewModel.ViewState.SubmitSucess -> {
//                        Toast.makeText(
//                            context,
//                            "Estabelecimento criado com sucesso",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                        navigateToSplash()
//                    }
//
//                    is ParceiroCadastroViewModel.ViewState.SubmitError -> {
//                        Toast.makeText(
//                            context,
//                            (viewState as ParceiroCadastroViewModel.ViewState.SubmitError).message,
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }
//                }


            }, texto = "Concluir")
        }
    }
    when (viewState) {
        ParceiroCadastroViewModel.ViewState.Loading -> {}
        ParceiroCadastroViewModel.ViewState.SubmitSucess -> {
            Toast.makeText(
                context,
                "Estabelecimento criado com sucesso",
                Toast.LENGTH_SHORT
            ).show()

            navigateToSplash()
        }

        is ParceiroCadastroViewModel.ViewState.SubmitError -> {
            Toast.makeText(
                context,
                (viewState as ParceiroCadastroViewModel.ViewState.SubmitError).message,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}

@Composable
fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}

@Composable
fun EstadoDropdown(
    selectedEstado: EstadoEnum,
    onEstadoSelected: (EstadoEnum) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val estados = EstadoEnum.values()

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedEstado.description,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Estado") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            estados.forEach { estado ->
                DropdownMenuItem(
                    text = { Text(estado.description) },
                    onClick = {
                        onEstadoSelected(estado)
                        expanded = false
                    }
                )
            }
        }
    }
}

//@Composable
//fun SpecializationDropdown(selectedSpecialty: String, onSpecialtySelected: (String) -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//    val options = listOf("Especialidade 1", "Especialidade 2", "Especialidade 3")
//
//    Box(modifier = Modifier.fillMaxWidth()) {
//        OutlinedTextField(
//            value = selectedSpecialty,
//            onValueChange = {},
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text("Especialidade") },
//            readOnly = true,
//            trailingIcon = {
//                IconButton(onClick = { expanded = !expanded }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowDropDown,
//                        contentDescription = null
//                    )
//                }
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            options.forEach { option ->
//                DropdownMenuItem(
//                    text = { Text(option) },
//                    onClick = {
//                        onSpecialtySelected(option)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Concluir")
    }
}