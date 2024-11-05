package br.sapiens.bellus_app.presentation.telas.clientSide.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.sections.carousel.SectionCarousel
import br.sapiens.bellus_app.presentation.ui.component.sections.newEstablishments.SectionEstablishments
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
    navigateToDetails: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val viewState by viewModel.uiState.collectAsState()
    var user by remember { mutableStateOf("") }
    val username = viewModel.username

    LaunchedEffect(Unit) {
        Log.d("TelaMarketplace", "LAUNCHED EFFECT ESTÁ SENDO EXECUTADO")
        viewModel.triggerEvent(MarketplaceViewModel.ViewEvent.LoadUserName)
//        user = (viewState as MarketplaceViewModel.ViewState.UserLoaded).name
        viewModel.triggerEvent(MarketplaceViewModel.ViewEvent.AvailableEstablishmentsLoad)
    }
    when (viewState) {
        MarketplaceViewModel.ViewState.Loading -> {
            Log.d("TelaMarketplace", "ViewState: Loading")
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center), color = MarronNaoSei)
            }
        }

        is MarketplaceViewModel.ViewState.UserLoaded -> {

        }

        is MarketplaceViewModel.ViewState.availableEstablishmentsLoad -> {
            Log.d("TelaMarketplace", "ViewState is UserLoaded")
            val items: List<AvailableEstablishment> =
                (viewState as MarketplaceViewModel.ViewState.availableEstablishmentsLoad).availableEstablishments

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                Text(
                    text = "Olá, ${username.value}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueNaoSei)
                        .padding(16.dp)
                )
                // TODO: O carrossel deveria mostar categorias, não estabelecimentos
                // TODO: Criar endpoint de categorias
                SectionCarousel(items)
                SectionEstablishments(
                    navigateToDetails,
                    items,
                    marketplaceStore = viewModel.mkt,
                    titulo = "Novo no Bellus"
                )
                SectionEstablishments(
                    navigateToDetails,
                    items,
                    marketplaceStore = viewModel.mkt,
                    titulo = "Ofertas especiais"
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }


    }
}