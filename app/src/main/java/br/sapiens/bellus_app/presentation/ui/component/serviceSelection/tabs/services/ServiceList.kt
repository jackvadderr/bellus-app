package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import kotlinx.coroutines.CoroutineScope

@Composable
fun ServiceList(
    items: List<ServiceDetails>,
    navigate: () -> Unit,
    coroutineScope: CoroutineScope,
    store: MarketplaceStore
) {
    Column {
        items.forEach { item ->
            ServiceItem(item, navigate, coroutineScope = coroutineScope, store = store)
            HorizontalDivider()
        }
    }
}