package br.sapiens.bellus_app.presentation.telas.pesquisa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.presentation.ui.component.WhyDontFuckingLoading
import br.sapiens.bellus_app.presentation.ui.component.sections.newEstablishments.CardEstablishments
import br.sapiens.bellus_app.presentation.viewmodels.SelectedCategoriesViewModel
import br.sapiens.bellus_app.utils.toAvailableEstablishment

@Composable
fun TelaSelectedCategories(
    viewModel: SelectedCategoriesViewModel,
    navigateToBack: () -> Unit,
    navigateToEstablishmentDetails: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()

    when (viewState) {
        SelectedCategoriesViewModel.ViewState.Loading -> {
            WhyDontFuckingLoading()
        }

        is SelectedCategoriesViewModel.ViewState.LoadCategories -> {
            viewModel.getEstablishments()
        }

        is SelectedCategoriesViewModel.ViewState.LoadEstablishments -> {
            val establishments: List<EstabelecimentoDTO> =
                (viewState as SelectedCategoriesViewModel.ViewState.LoadEstablishments).establishments
            val availableEstablishments = establishments.map { it.toAvailableEstablishment() }
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(availableEstablishments) { establishment ->
                    CardEstablishments(
                        navigateToDetails = navigateToEstablishmentDetails,
                        item = establishment,
                        marketplaceStore = viewModel.mkt,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}


//@Composable
//fun CategoriaEstablishmentCard(barbearia: Barbearia) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation()
//    ) {
//        Column {
//            Image(
//                painter = painterResource(id = barbearia.imageResId),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(180.dp),
//                contentScale = ContentScale.Crop
//            )
//            Row(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text(
//                        text = barbearia.name,
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    Text(
//                        text = barbearia.address,
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//                RatingBox(rating = barbearia.rating, reviewCount = barbearia.reviewCount)
//            }
//        }
//    }
//}
//
//@Composable
//fun RatingBox(rating: Float, reviewCount: Int) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(4.dp))
//            .padding(8.dp)
//    ) {
//        Text(text = rating.toString(), style = MaterialTheme.typography.titleMedium)
//        Text(text = "$reviewCount avaliações", style = MaterialTheme.typography.bodySmall)
//    }
//}
//
//data class Barbearia(
//    val name: String,
//    val address: String,
//    val imageResId: Int,
//    val rating: Float,
//    val reviewCount: Int
//)
//
//val barbearias = listOf(
//    Barbearia(
//        "A Navalha Dourada",
//        "Rua João Pedro da Rocha, 1545, 76820-110, Porto Velho (RO)",
//        R.mipmap.barbearia,
//        5.0f,
//        100
//    ),
//    Barbearia(
//        "Cortes & Estilos",
//        "Av. Sete de Setembro, 1234, 76804-123, Porto Velho (RO)",
//        R.mipmap.barbearia,
//        4.6f,
//        10
//    )
//)
