package br.sapiens.bellus_app.presentation.telas.clientSide.marketplaceSelection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.ImageSlider
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about.AboutTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio.PortfolioTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.review.ReviewsTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceInfoSection
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceList
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceSelectionViewModel

@Composable
fun TelaSelectionMarketplace(
    viewModel: MarketplaceSelectionViewModel,
//    navigateToSelectionProfissional: () -> Unit,
    navigateToCreateAppointment: () -> Unit,
    navigateToBack: () -> Unit
) {
    val viewState by viewModel.uiState.collectAsState()


    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.triggerEvent(MarketplaceSelectionViewModel.ViewEvent.LoadUser)
    }

    when (viewState) {
        is MarketplaceSelectionViewModel.ViewState.Loading -> {
            Log.d("TelaServiceSelection", "ViewState: Loading")
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center), color = MarronNaoSei)
            }
        }

        is MarketplaceSelectionViewModel.ViewState.UserLoaded -> {
            val currentEstablishmentDetails =
                (viewState as MarketplaceSelectionViewModel.ViewState.UserLoaded).establishmentDetails
            val itemsServiceDetails: List<ServiceDetails> =
                (viewState as MarketplaceSelectionViewModel.ViewState.UserLoaded).serviceDetails
            val itemsReviewsDetails =
                (viewState as MarketplaceSelectionViewModel.ViewState.UserLoaded).reviewsDetails

            Box(modifier = Modifier.fillMaxSize()) {
                ImageSlider(
                    urls = currentEstablishmentDetails.imageResource,
                    contentDescription = "", // TODO: Database
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { navigateToBack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 230.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    ServiceInfoSection(
                        currentEstablishmentDetails.name,
                        currentEstablishmentDetails.address,
                        currentEstablishmentDetails.horario_funcionamento
                    )

                    val tabTitles = listOf("Serviços", "Avaliações", "Portfólio", "Sobre")
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                        contentColor = MarronNaoSei,
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title) }
                            )
                        }
                    }

                    when (selectedTabIndex) {
                        0 -> ServiceList(
                            itemsServiceDetails,
                            navigateToCreateAppointment,
                            store = viewModel.mkt,
                            coroutineScope = viewModel.scope,
                            theText = "Agendar"
                        )

                        1 -> ReviewsTab(
                            itemsReviewsDetails = itemsReviewsDetails,
                            averagedReviewsDetails = currentEstablishmentDetails.rating,
                            totalReviewsDetails = currentEstablishmentDetails.totalReviews
                        )

                        2 -> PortfolioTab(currentEstablishmentDetails.portfolio)
                        3 -> AboutTab(
                            currentEstablishmentDetails.description,
                            currentEstablishmentDetails.telefone,
                            currentEstablishmentDetails.horario_funcionamento
                        )
                    }
                }
            }
        }
    }
}
