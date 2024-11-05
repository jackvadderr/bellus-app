package br.sapiens.bellus_app.presentation.telas.professionalSide

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.ParceiroImageUpdateSlider
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about.AboutTabParceiro
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio.PortfolioTabParceiro
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.ServiceInfoSection
import br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services.UpdateServiceListParceiro
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.parceiroSide.ParceiroUpdateEstablishmentViewModel
import java.util.UUID

@Composable
fun TelaUpdateEstablishmentParceiro(
    viewModel: ParceiroUpdateEstablishmentViewModel,
    navigateToUpdateService: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val imageUrlToDownload = remember { mutableStateOf<String?>("") }
    val isPortfolio = remember { mutableStateOf<Boolean>(false) }


    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("TelaUpdateEstablishmentParceiro", "Camera permission granted")
        } else {
            Log.d("TelaUpdateEstablishmentParceiro", "Camera permission denied")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { it ->
            Log.d("TelaUpdateEstablishmentParceiro", "Selected image URI: $uri")
            val uploadLiveData = viewModel.firebaseStorageProvider.uploadImageAndGetUrl(
                it,
                "images/${UUID.randomUUID()}.jpg"
            )
            uploadLiveData.observeForever { downloadUrl ->
                if (downloadUrl != null) {
                    imageUrlToDownload.value = downloadUrl
                    Log.d("TelaUpdateEstablishmentParceiro", "URL TO DOWNLOAD: $downloadUrl")
                } else {
                    Log.e("TelaUpdateEstablishmentParceiro", "Failed to upload image")
                }
            }
            Log.d("TelaUpdateEstablishmentParceiro", "URL TO DOWNLOAD: ${imageUrlToDownload.value}")
        }
    }


    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Log.d("TelaUpdateEstablishmentParceiro", "Gallery permission denied")
        }
    }

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

                // Dentro do seu Composable
                LaunchedEffect(imageUrlToDownload.value) {

                    if (imageUrlToDownload.value?.isNotEmpty() == true) {
                        if (isPortfolio.value == false) {
                            Log.d(
                                "TelaUpdateEstablishmentParceiro",
                                "URL TO DOWNLOAD: ${imageUrlToDownload.value}"
                            )
                            val updatedEstablishmentDetail = EstablishmentDetail(
                                id = currentEstablishmentDetails.id,
                                cnjp = currentEstablishmentDetails.cnjp,
                                name = currentEstablishmentDetails.name,
                                address = currentEstablishmentDetails.address,
                                telefone = currentEstablishmentDetails.telefone,
                                horario_funcionamento = currentEstablishmentDetails.horario_funcionamento,
                                rating = currentEstablishmentDetails.rating,
                                imageResource = listOf(imageUrlToDownload.value.toString()),
                                portfolio = currentEstablishmentDetails.portfolio,
                                description = currentEstablishmentDetails.description,
                                profisisonal_dono = currentEstablishmentDetails.profisisonal_dono,
                                profissionais_filiados = currentEstablishmentDetails.profissionais_filiados,
                                totalReviews = 0,
                            )
                            viewModel.triggerEvent(
                                ParceiroUpdateEstablishmentViewModel.ViewEvent.UpdateEstablishment(
                                    updatedEstablishmentDetail
                                )
                            )
                        } else if (isPortfolio.value == true) {
                            Log.d(
                                "TelaUpdateEstablishmentParceiro",
                                "URL TO DOWNLOAD: ${imageUrlToDownload.value}"
                            )
                            val updatedEstablishmentDetail = EstablishmentDetail(
                                id = currentEstablishmentDetails.id,
                                cnjp = currentEstablishmentDetails.cnjp,
                                name = currentEstablishmentDetails.name,
                                address = currentEstablishmentDetails.address,
                                telefone = currentEstablishmentDetails.telefone,
                                horario_funcionamento = currentEstablishmentDetails.horario_funcionamento,
                                rating = currentEstablishmentDetails.rating,
                                imageResource = currentEstablishmentDetails.imageResource,
                                portfolio = listOf(imageUrlToDownload.value.toString()),
                                description = currentEstablishmentDetails.description,
                                profisisonal_dono = currentEstablishmentDetails.profisisonal_dono,
                                profissionais_filiados = currentEstablishmentDetails.profissionais_filiados,
                                totalReviews = 0,
                            )
                            viewModel.triggerEvent(
                                ParceiroUpdateEstablishmentViewModel.ViewEvent.UpdateEstablishment(
                                    updatedEstablishmentDetail
                                )
                            )
                        }


                    } else {
                        Log.d("TelaUpdateEstablishmentParceiro", "NÃO DEU BOM?")
                    }
                }

                ParceiroImageUpdateSlider(
                    urls = currentEstablishmentDetails.imageResource,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop,
                    onAddImageClick = {
                        isPortfolio.value = false
                        viewModel.triggerEvent(
                            ParceiroUpdateEstablishmentViewModel.ViewEvent.OpenGallery(
                                context = viewModel.myContext,
                                permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                                launcher = galleryPermissionLauncher
                            )
                        )
                    }
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


                        1 -> PortfolioTabParceiro(
                            establishments = currentEstablishmentDetails,
                            openGallery = {
                                isPortfolio.value = true
                                viewModel.triggerEvent(
                                    ParceiroUpdateEstablishmentViewModel.ViewEvent.OpenGallery(
                                        context = viewModel.myContext,
                                        permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                                        launcher = galleryPermissionLauncher
                                    )
                                )

                            },
                        )

                        2 -> AboutTabParceiro(
//                            currentEstablishmentDetails.description,
//                            currentEstablishmentDetails.telefone,
//                            currentEstablishmentDetails.horario_funcionamento
                            currentEstablishmentDetails,
                            onSaveClick = {
                                viewModel.triggerEvent(
                                    ParceiroUpdateEstablishmentViewModel.ViewEvent.UpdateAboutEstablihsment(
                                        it
                                    )
                                )
                            }
                        )
                    }
                }
            }

            ParceiroUpdateEstablishmentViewModel.ViewState.EstablishmentUpdated -> {}
            is ParceiroUpdateEstablishmentViewModel.ViewState.LoadedCurrentEstablishmentId -> {}
        }
    }
}
