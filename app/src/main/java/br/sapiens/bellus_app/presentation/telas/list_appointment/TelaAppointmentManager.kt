package br.sapiens.bellus_app.presentation.telas.list_appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.appointment.AgendamentoCard
import br.sapiens.bellus_app.presentation.ui.component.appointment.EmptyState
import br.sapiens.bellus_app.presentation.viewmodels.CreateAppointmentViewModel

@Composable
fun TelaAppointmentManager(
    viewModel: CreateAppointmentViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Agendamentos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Próximos agendamentos
        Text(
            text = "Próximos (1)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        AgendamentoCard(
            date = "Quarta-feira 20",
            month = "Março",
            name = "A Navalha Dourada",
            time = "09:00 - 09:50",
            price = "R$ 50,00"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Agendamentos anteriores
        Text(
            text = "Anteriores",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        EmptyState()
    }
}
