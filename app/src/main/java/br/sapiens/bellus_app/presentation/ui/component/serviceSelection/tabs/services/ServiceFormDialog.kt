package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import CustomButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

@Composable
fun ServiceFormDialog(
    onDismiss: () -> Unit,
    onConfirm: (ServiceDetails) -> Unit,
    service: ServiceDetails? = null
) {
    val id = remember { mutableStateOf<String>(service?.id ?: "") }
    val name = remember { mutableStateOf(service?.name ?: "") }
    val duration: MutableState<Duration> = remember {
        mutableStateOf(
            service?.duration ?: Duration(
                type = "",
                value = 0.0f
            )
        )
    }
    val price: MutableState<Float> = remember { mutableFloatStateOf(service?.preco ?: 0.0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (service == null) "Criar Serviço" else "Atualizar Serviço") },
        text = {
            Column {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = duration.value.toString(),
                    onValueChange = { duration.value = (it.toIntOrNull() ?: 0) as Duration },
                    label = { Text("Duração (minutos)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price.value.toString(),
                    onValueChange = { price.value = ((it.toDoubleOrNull() ?: 0.0).toFloat()) },
                    label = { Text("Preço (R$)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    onConfirm(
                        ServiceDetails(
                            id = id.value,
                            name = name.value,
                            duration = duration.value,
                            preco = price.value
                        )
                    )
                    onDismiss()
                },
                texto = "Confirmar"
            )
        },
        dismissButton = {
            CustomButton(onClick = onDismiss, "Cancelar")
        }
    )
}