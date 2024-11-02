package br.sapiens.bellus_app.presentation.telas.professionalSide

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.ParceiroImageUpdateSlider
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about.AboutTab
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio.PortfolioTabParceiro
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceInfoSection
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.UpdateServiceListParceiro
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.ParceiroUpdateEstablishmentViewModel

@Composable
fun TelaUpdateEstablishmentParceiro(
    viewModel: ParceiroUpdateEstablishmentViewModel,
    navigateToUpdateService: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (viewState) {
            is ParceiroUpdateEstablishmentViewModel.ViewState.Loading -> {
                Log.d("TelaServiceSelection", "ViewState: Loading")
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center),
                        color = MarronNaoSei
                    )
                }
            }

            is ParceiroUpdateEstablishmentViewModel.ViewState.LoadedCurrentEstablishment -> {
                Log.d("TelaUpdateEstablishmentParceiro", "ViewState: LoadedCurrentEstablishment")
                val currentEstablishmentDetails =
                    (viewState as ParceiroUpdateEstablishmentViewModel.ViewState.LoadedCurrentEstablishment).currentEstablishment
                val itemsServiceDetails =
                    (viewState as ParceiroUpdateEstablishmentViewModel.ViewState.LoadedCurrentEstablishment).services

                ParceiroImageUpdateSlider(
                    urls = currentEstablishmentDetails.imageResource,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop,
                    onAddImageClick = { /*viewModel.triggerEvent(ParceiroUpdateEstablishmentViewModel.ViewEvent.AddImage)*/ }
                )

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

                    val tabTitles = listOf("Serviços", "Portfólio", "Sobre")
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
                        0 -> UpdateServiceListParceiro(
                            itemsServiceDetails,
                            coroutineScope = viewModel.scope,
                            store = viewModel.mkt,
                            theText = "Editar",
                            onServiceUpdated = {
                                viewModel.triggerEvent(
                                    ParceiroUpdateEstablishmentViewModel.ViewEvent.UpdateService(it)
                                )
                            },
                            onServiceCreated = {
                                viewModel.triggerEvent(
                                    ParceiroUpdateEstablishmentViewModel.ViewEvent.CreateService(it)
                                )
                            },
                            onServiceDeleted = {
                                viewModel.triggerEvent(
                                    ParceiroUpdateEstablishmentViewModel.ViewEvent.DeleteService(it)
                                )
                            }
                        )


                        1 -> PortfolioTabParceiro(currentEstablishmentDetails.portfolio)
                        2 -> AboutTab(
                            currentEstablishmentDetails.description,
                            currentEstablishmentDetails.telefone,
                            currentEstablishmentDetails.horario_funcionamento
                        )
                    }
                }
            }

            ParceiroUpdateEstablishmentViewModel.ViewState.EstablishmentUpdated -> {}
            is ParceiroUpdateEstablishmentViewModel.ViewState.LoadedCurrentEstablishmentId -> {}
        }
    }
}
