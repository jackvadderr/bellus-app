package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.dominio.model.event.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ServiceList(
    items: List<ServiceDetails>,
    navigate: () -> Unit,
    coroutineScope: CoroutineScope,
    store: MarketplaceStore,
    theText: String,
) {
    Column {
        items.forEach { item ->
            ServiceItem(
                item,
                navigate,
                coroutineScope = coroutineScope,
                store = store,
                theText = theText
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun UpdateServiceListParceiro(
    items: List<ServiceDetails>,
    coroutineScope: CoroutineScope,
    store: MarketplaceStore,
    theText: String,
) {
    val showDialog = remember { mutableStateOf(false) }
    val currentService = remember { mutableStateOf<ServiceDetails?>(null) }

    Column {
        items.forEach { item ->
            ServiceItem(
                item,
                navigate = {
                    currentService.value = item
                    showDialog.value = true
                },
                coroutineScope = coroutineScope,
                store = store,
                theText = theText
            )
            HorizontalDivider()
        }
    }
    CreateNewServiceItem(navigate = {
        currentService.value = null
        showDialog.value = true
    })

    if (showDialog.value) {
        ServiceFormDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = { service ->
                coroutineScope.launch {
                    if (currentService.value == null) {
                        store.dispatch(MarketplaceEvent.SuccessCreateService(service))
                    } else {
                        store.dispatch(MarketplaceEvent.SuccessUpdateService(service))
                    }
                }
            },
            service = currentService.value
        )
    }
}

@Composable
fun CreateNewServiceItem(
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navigate() }
    ) {
        Text(
            text = "Adicionar novo serviço",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}