package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import CustomButton
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import kotlinx.coroutines.CoroutineScope


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
    onServiceUpdated: (ServiceDetails) -> Unit,
    onServiceCreated: (ServiceDetails) -> Unit,
    onServiceDeleted: (ServiceDetails) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }
    val currentService = remember { mutableStateOf<ServiceDetails?>(null) }

    Column {
        if (items.isNotEmpty()) {
            items.forEach { item ->
                ParceiroServiceItem(
                    item,
                    navigate = {
                        currentService.value = item
                        showDialog.value = true
                    },
                    coroutineScope = coroutineScope,
                    store = store,
                    buttonOneText = theText
                )
                HorizontalDivider()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nenhum serviço disponível, crie um novo!",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        CreateNewServiceItem(navigate = {
            currentService.value = null
            showDialog.value = true
        })
    }



    if (showDialog.value) {
        ServiceFormDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = { service ->
                if (service.id.isEmpty()) {
                    Log.d("ServiceList", "Serviço Criaado?")
                    onServiceCreated(service)
                } else if (service.id.isNotEmpty()) {
                    Log.d("ServiceList", "Serviço Atualizado?")
                    onServiceUpdated(service)
                }
            },
            onDelete = { service ->
                onServiceDeleted(service)
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
        CustomButton(
            onClick = navigate,
            texto = "Adicionar novo serviço"
        )
    }
}