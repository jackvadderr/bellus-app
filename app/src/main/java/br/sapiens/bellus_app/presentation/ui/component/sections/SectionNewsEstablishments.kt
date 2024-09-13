package br.sapiens.bellus_app.presentation.ui.component.sections

import LoadImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel


@Composable
fun NewInBellusSection(newItems: List<NewItemModel>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Novo no Bellus", style = MaterialTheme.typography.bodySmall)
        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(newItems) { item ->
                NewInBellusCard(item)
            }
        }
    }
}

@Composable
fun NewInBellusCard(item: NewItemModel) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp)
            .clickable { /* Ação ao clicar */ },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            LoadImage(
                url = item.imageResource,
                contentDescription = item.name,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

