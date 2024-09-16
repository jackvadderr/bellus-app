package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ServiceInfoSection() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A Navalha Dourada",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Rua João Pedro da Rocha, 1545, 76820-110, Porto Velho (RO)",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Aberto até 18:00",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}