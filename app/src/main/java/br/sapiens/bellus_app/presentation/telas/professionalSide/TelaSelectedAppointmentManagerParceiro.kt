package br.sapiens.bellus_app.presentation.telas.professionalSide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.viewmodels.ParceiroAppointmentViewModel

@Composable
fun TelaSelectedAppointmentManagerParceiro(
    viewModel: ParceiroAppointmentViewModel
) {

    val viewState by viewModel.uiState.collectAsState()
    val theAction = remember { mutableStateOf<String>("") }

    when (viewState) {
        ParceiroAppointmentViewModel.ViewState.Error -> TODO()
        is ParceiroAppointmentViewModel.ViewState.EstablishmentId -> TODO()
        is ParceiroAppointmentViewModel.ViewState.LoadedAppointments -> TODO()
        ParceiroAppointmentViewModel.ViewState.Loading -> TODO()
        is ParceiroAppointmentViewModel.ViewState.UpdateAppointment -> TODO()
        is ParceiroAppointmentViewModel.ViewState.LoadedAppointment -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    HeaderSection(name = "João Ricardo")
                    Spacer(modifier = Modifier.height(16.dp))
                    AppointmentDetails(
                        date = "Quarta-feira, 22 de Março de 2024 às 16:00.",
                        duration = "Duração de 50 minutos."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ActionButtonsSection(action = theAction.toString())
                    Spacer(modifier = Modifier.height(16.dp))
                    SummarySection(service = "Corte + Barba", price = "R$ 50,00")
                }
                ConfirmationSection()
            }
        }
    }

//    AppointmentScreen(action = "")
}


@Composable
fun AppointmentScreen(
    action: String
) {

}

@Composable
fun HeaderSection(name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /* TODO: Back Action */ }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            // Placeholder for Profile Image
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = name.take(1), // First letter of the name
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun AppointmentDetails(date: String, duration: String) {
    Column {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = duration,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ActionButtonsSection(
    action: String
) {
    Column {
        ActionButton(text = "Aceitar", onClick = { /* Handle action "accepted" */ })
        ActionButton(text = "Recusar", onClick = { /* Handle action "rejected" */ })
        ActionButton(text = "Completar", onClick = { /* Handle action "completed" */ })
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF795548), // Custom brown color
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

// 4. Summary Section
@Composable
fun SummarySection(service: String, price: String) {
    Column {
        Text(
            text = "Resumo",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$service\n$price",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// 5. Confirmation Section
@Composable
fun ConfirmationSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Serviço finalizado?",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* TODO: Handle YES action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
            ) {
                Text("SIM")
            }
            Button(
                onClick = { /* TODO: Handle NO action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
            ) {
                Text("NÃO")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAppointmentScreen() {
//    AppointmentScreen()
//}
