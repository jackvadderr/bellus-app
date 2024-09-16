package br.sapiens.bellus_app.presentation.telas.home

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about.AboutTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio.PortfolioTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.review.ReviewsTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceInfoSection
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceList
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.ServiceSelectionViewModel

@Composable
fun TelaServiceSelection(
    viewModel: ServiceSelectionViewModel,
) {
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.triggerEvent(ServiceSelectionViewModel.ViewEvent.LoadUser)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.mipmap.barbearia),
            contentDescription = "Barber Shop Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { },
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

            ServiceInfoSection()

            var selectedTabIndex by remember { mutableIntStateOf(0) }
            val tabTitles = listOf("Serviços", "Avaliações", "Portfólio", "Sobre")
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                contentColor = MarronNaoSei
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (viewState) {
                is ServiceSelectionViewModel.ViewState.Loading -> {
                    Log.d("TelaServiceSelection", "ViewState: Loading")
                    // TODO: Exibir um indicador de carregamento
                }

                is ServiceSelectionViewModel.ViewState.UserLoaded -> {
                    Log.d("TelaServiceSelection", "ViewState: UserLoaded")
                    val itemsEstablishmentDetails =
                        (viewState as ServiceSelectionViewModel.ViewState.UserLoaded).establishmentDetails
                    val itemsServiceDetails: List<ServiceDetails> =
                        (viewState as ServiceSelectionViewModel.ViewState.UserLoaded).serviceDetails

                    when (selectedTabIndex) {
                        0 -> ServiceList(itemsServiceDetails)
                        1 -> ReviewsTab()
                        2 -> PortfolioTab()
                        3 -> AboutTab()
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ServiceSelectionScreenPreview() {
//    MaterialTheme {
//        ServiceSelectionScreen()
//    }
//}
