package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

@Composable
fun TeamSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Membros da Equipe",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Jorge Marcos")
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Gabriel Peixoto")
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Lucas Silva")
        }
    }
}