package br.sapiens.bellus_app.presentation.ui.component.sections.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei

@Composable
fun SectionCarousel(categories: List<EstablishmentDetails>) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Garante que o fundo azul ocupe toda a largura
            .background(BlueNaoSei) // Fundo azul
    ) {
        if (categories.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth() // Preenche toda a largura da tela
            ) {
                items(categories) { category ->
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(120.dp)
                            .padding(0.dp)
                    ) {
                        CarouselCard(category)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sem itens disponíveis",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

