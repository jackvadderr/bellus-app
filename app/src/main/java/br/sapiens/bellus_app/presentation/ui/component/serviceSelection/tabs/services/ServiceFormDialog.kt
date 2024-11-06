package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import CustomButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.CustomOutlinedTextField
import br.sapiens.bellus_app.presentation.ui.component.GeralTextField
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

@Composable
fun ServiceFormDialog(
    onDismiss: () -> Unit,
    onConfirm: (ServiceDetails) -> Unit,
    onDelete: (ServiceDetails) -> Unit,
    service: ServiceDetails? = null,
) {
    val id = remember { mutableStateOf(service?.id ?: "") }
    val name = remember { mutableStateOf(service?.name ?: "") }
    val description = remember { mutableStateOf(service?.description ?: "") }
    val durationType = remember { mutableStateOf(service?.duration?.type ?: "") }
    val durationValue = remember { mutableFloatStateOf(service?.duration?.value ?: 0.0f) }
    val price: MutableState<Float> = remember { mutableFloatStateOf(service?.preco ?: 0.0f) }

    val durationOptions = listOf("Hour", "Minute")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (service == null) "Criar Serviço" else "Atualizar Serviço") },
        text = {
            Column {
                GeralTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = "Nome",
                    modifier = Modifier.height(56.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                GeralTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = "Descrição",
                    modifier = Modifier.height(56.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    CustomOutlinedTextField(
                        selectedSpecialty = durationType.value,
                        onSpecialtySelected = { durationType.value = it },
                        options = durationOptions,
                        placeholder = "Duração",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    GeralTextField(
                        value = if (durationValue.floatValue == 0.0f) "" else durationValue.floatValue.toString(),
                        onValueChange = { newValue ->
                            val floatValue =
                                newValue.toFloatOrNull() ?: newValue.toIntOrNull()?.toFloat()
                                ?: 0.0f
                            durationValue.floatValue = floatValue
                        },
                        placeholder =
                        if (durationType.value == "Hour") {
                            "Horas"
                        } else if (durationType.value == "Minutes") {
                            "Minutos"
                        } else {
                            "Hora/Minuto?"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                GeralTextField(
                    value = if (price.value == 0.0f) "" else price.value.toString(),
                    onValueChange = { price.value = ((it.toDoubleOrNull() ?: 0.0).toFloat()) },
                    placeholder = "Preço (R$)",
                    modifier = Modifier.height(56.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

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
                            description = description.value,
                            duration = Duration(
                                type = durationType.value,
                                value = durationValue.floatValue
                            ),
                            preco = price.value,
                            establishmentId = ""
                        )
                    )
                    onDismiss()

                },
                texto = "Confirmar"
            )
        },
        dismissButton = {
            Row {
                if (service != null) {
                    CustomButton(
                        onClick = {
                            onDelete(service)
                            onDismiss()
                        },
                        texto = "Apagar",
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(120.dp)
                            .height(40.dp),
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                }
                CustomButton(
                    onClick = onDismiss, texto = "Cancelar",
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .width(120.dp)
                        .height(40.dp),
                )

            }
        }
    )
}