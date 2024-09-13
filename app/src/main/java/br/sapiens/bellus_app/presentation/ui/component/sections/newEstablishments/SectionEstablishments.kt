package br.sapiens.bellus_app.presentation.ui.component.sections.newEstablishments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel


@Composable
fun SectionEstablishments(newItems: List<NewItemModel>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Novo no Bellus", style = MaterialTheme.typography.bodySmall)
        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(newItems) { item ->
                CardEstablishments(item)
            }
        }
    }
}