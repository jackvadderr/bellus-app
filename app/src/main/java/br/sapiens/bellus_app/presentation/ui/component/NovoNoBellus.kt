package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

data class NewItem(
    val name: String,
    val location: String,
    val imageRes: Int
)

@Composable
fun NewInBellusSection() {
    val newItems = listOf(
        NewItem("Cortes & Estilos", "Av. Sete de Setembro, 1234, Porto Velho", R.mipmap.barbearia_1),
        NewItem("Cortes Clássicos", "Rua início de favela, Porto Velho", R.mipmap.barbearia_1)
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Novo no Bellus", style = MaterialTheme.typography.h6)
        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(newItems) { item ->
                NewInBellusItem(item)
            }
        }
    }
}

@Composable
fun NewInBellusItem(item: NewItem) {
    val itemWidth = 80.dp
    val itemHeight = 100.dp
    val imageSize = 60.dp
    val textSize = MaterialTheme.typography.body2
    Card(
        modifier = Modifier
            .width(itemWidth)
            .height(itemHeight)
            .clickable { /* Ação ao clicar */ }
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(text = item.name, style = textSize)
            Text(text = item.location, style = textSize)
        }
    }
}
