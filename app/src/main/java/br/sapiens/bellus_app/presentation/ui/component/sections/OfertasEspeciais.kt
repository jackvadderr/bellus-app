package br.sapiens.bellus_app.presentation.ui.component.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

data class Offer(
    val title: String,
    val address: String,
    val imageResource: Int
)

@Composable
fun OfferCard(item: Offer) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp)
            .clickable { /* Ação ao clicar */ },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = item.imageResource),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.height(120.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(text = item.address, style = MaterialTheme.typography.titleSmall)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.7f), shape = CircleShape)
                    .padding(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                    Text(text = "4.5", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SpecialOffersSection(
    offers: List<Offer> = sampleOffers,
) {
    val newItems = listOf(
        NewItemModel("Cortes & Estilos", "Av. Sete de Setembro, 1234, Porto Velho", R.mipmap.barbearia_1),
        NewItemModel("Cortes Clássicos", "Rua início de favela, Porto Velho", R.mipmap.barbearia_1)
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Ofertas Especiais", style = MaterialTheme.typography.titleSmall)
        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(newItems) { item ->
                NewInBellusCard(item)
            }
        }
    }
}


// Dados de exemplo
val sampleOffers = listOf(
    Offer("A Navalha Dourada", "Rua João Pedro da Rocha, 1545, Porto Velho", R.mipmap.barbearia_1),
    Offer("Barbearia", "Rua Industrial, Porto Velho", R.mipmap.barbearia_1)
)
