package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WorkingHoursSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Horário de Funcionamento",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Segunda-feira       8:00 - 18:00",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Terça-feira             8:00 - 18:00",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quarta-feira          8:00 - 18:00",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quinta-feira           8:00 - 18:00",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sexta-feira           8:00 - 18:00",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}