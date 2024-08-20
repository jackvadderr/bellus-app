package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

data class Offer(
    val title: String,
    val address: String,
    val imageResource: Int
)

@Composable
fun OfferImage(imageResource: Int, width: Dp, height: Dp) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun OfferCard(offer: Offer, imageWidth: Dp, imageHeight: Dp) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Ação de clique */ }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centraliza o conteúdo horizontalmente
            modifier = Modifier.padding(8.dp) // Adiciona padding interno ao cartão
        ) {
            OfferImage(
                imageResource = offer.imageResource,
                width = imageWidth,
                height = imageHeight
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espaçamento entre a imagem e o texto
            Text(
                text = offer.title,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = offer.address,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SpecialOffersSection(
    offers: List<Offer> = sampleOffers,
    imageWidth: Dp = defaultImageWidth,
    imageHeight: Dp = defaultImageHeight
) {
    Column(modifier = Modifier.padding(sectionPadding)) {
        Text(text = "Ofertas especiais", style = MaterialTheme.typography.h6)
        LazyRow(
            contentPadding = PaddingValues(vertical = rowVerticalPadding),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            items(offers) { offer ->
                OfferCard(
                    offer = offer,
                    imageWidth = imageWidth,
                    imageHeight = imageHeight
                )
            }
        }
    }
}


private val defaultImageWidth = 120.dp
private val defaultImageHeight = 80.dp
private val cornerRadius = 8.dp
private val sectionPadding = 16.dp
private val rowVerticalPadding = 8.dp
private val itemSpacing = 16.dp

// Dados de exemplo
val sampleOffers = listOf(
    Offer("A Navalha Dourada", "Rua João Pedro da Rocha, 1545, Porto Velho", R.mipmap.barbearia_1),
    Offer("Barbearia", "Rua Industrial, Porto Velho", R.mipmap.barbearia_1)
)
