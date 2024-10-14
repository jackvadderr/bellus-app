package br.sapiens.bellus_app.presentation.ui.component.appointment

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import br.sapiens.bellus_app.presentation.ui.model.ServicePost
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei

@Composable
fun Header(navigateToBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = { navigateToBack() }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = MarronNaoSei
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Fazer uma reserva",
            style = MaterialTheme.typography.labelMedium,
            color = MarronNaoSei
        )
    }
}

@Composable
fun CalendarView(modifier: Modifier = Modifier, onDateSelected: (Long) -> Unit) {
    var selectedDate by remember {
        mutableStateOf(System.currentTimeMillis())
    }
    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                android.widget.CalendarView(context).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val calendar = Calendar.getInstance()
                        calendar.set(year, month, dayOfMonth)
//                        calendar.add(Calendar.DAY_OF_MONTH, -1)
                        selectedDate = calendar.timeInMillis
                        onDateSelected(selectedDate)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TimeSlotSelector(onTimeeSlotSelected: (String) -> Unit) {
    val timeSlots = listOf("8:00", "9:00", "10:00", "11:00", "14:00", "15:00", "16:00")
    var selectedSlot by remember { mutableStateOf("") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(timeSlots) { slot ->
            val isSelected = selectedSlot == slot
            Button(
                onClick = {
                    selectedSlot = slot
                    onTimeeSlotSelected(slot)
                },
                colors = ButtonColors(
                    containerColor = if (isSelected) BlueNaoSei else MarronNaoSei,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFAAAAAA),
                    disabledContentColor = Color(0xFFAAAAAA)
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(text = slot, color = Color.White)
            }
        }
    }
}

@Composable
fun ServiceSummary(
    service: ServicePost,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${service.name} com Lucas da Silva", color = Color.Gray)
            Text(text = "R$ ${service.preco}", color = Color.Black)
            Text(text = "${service.duration.value} ${service.duration.type}", color = Color.Gray)
        }
    }
}

@Composable
fun Footer(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "R$ 50,00", style = MaterialTheme.typography.labelMedium)
        Button(
            onClick = { onClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = MarronNaoSei,
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFAAAAAA),
                disabledContentColor = Color(0xFFAAAAAA)
            )
        ) {
            Text(text = "Agendar")
        }
    }
}
