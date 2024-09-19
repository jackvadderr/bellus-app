package br.sapiens.bellus_app.presentation.telas.home

import SectionSpecialOffers
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.sections.carousel.SectionCarousel
import br.sapiens.bellus_app.presentation.ui.component.sections.newEstablishments.SectionEstablishments
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
    navigateToDetails: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.triggerEvent(MarketplaceViewModel.ViewEvent.LoadUser)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Text(
            // TODO: Utilizar DataStore para salvar informações do usuário para não mandar requisição toda vez que entrar na tela
            text = "Olá, Usuário",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueNaoSei)
                .padding(16.dp)
        )
        when (viewState) {
            is MarketplaceViewModel.ViewState.Loading -> {
                Log.d("TelaMarketplace", "ViewState is Loading")
            }

            is MarketplaceViewModel.ViewState.UserLoaded -> {
                Log.d("TelaMarketplace", "ViewState is UserLoaded")
                val items: List<AvailableEstablishment> =
                    (viewState as MarketplaceViewModel.ViewState.UserLoaded).availableEstablishments

                // TODO: O carrossel deveria mostar categorias, não estabelecimentos
                // TODO: Criar endpoint de categorias
                SectionCarousel(items)
                SectionSpecialOffers(items)
                SectionEstablishments(
                    navigateToDetails,
                    items,
                    marketplaceStore = viewModel.mkt
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}