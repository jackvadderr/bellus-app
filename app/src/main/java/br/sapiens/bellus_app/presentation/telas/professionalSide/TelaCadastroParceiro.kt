package br.sapiens.bellus_app.presentation.telas.professionalSide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.viewmodels.ParceiroCadastroViewModel


@Composable
fun CadastroParceiro(
    viewModel: ParceiroCadastroViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Address Information
        FormTextField(label = "Bairro")
        FormTextField(label = "Endereço")
        Row(modifier = Modifier.fillMaxWidth()) {
            FormTextField(
                label = "Número",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            FormTextField(
                label = "Complemento",
                modifier = Modifier.weight(1f)
            )
        }

        // CNPJ Field with validation (example mask can be added)
        FormTextField(
            label = "CNPJ (Obrigatório)",
            keyboardType = KeyboardType.Number
        )

        // Social Information
        FormTextField(label = "Razão Social")
        FormTextField(label = "Nome do Estabelecimento")
        FormTextField(
            label = "Celular ou Telefone do Estabelecimento",
            keyboardType = KeyboardType.Phone
        )

        // Specialization Dropdown
        SpecializationDropdown()

        // Submit Button
        SubmitButton() // O botão deve enviar direto para outro perfil
    }

}

@Composable
fun FormTextField(
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}

@Composable
fun SpecializationDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedSpecialty by remember { mutableStateOf("Selecione a sua especialidade") }
    val options = listOf("Especialidade 1", "Especialidade 2", "Especialidade 3")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedSpecialty,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Especialidade") },
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
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedSpecialty = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SubmitButton() {
    Button(
        onClick = { /* Handle form submission */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Concluir")
    }
}