package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.dominio.model.event.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.utils.formatDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ServiceItem(
    item: ServiceDetails,
    navigate: () -> Unit,
    store: MarketplaceStore,
    coroutineScope: CoroutineScope
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = formatDuration(item.duration),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "a partir de R$ ${item.preco}", style = MaterialTheme.typography.bodyMedium)
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    store.dispatch(
                        MarketplaceEvent.SucessGetCurrentService(
                            item
                        )
                    )
                }

                navigate()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MarronNaoSei,
                contentColor = Color.White
            )
        ) {
            Text(text = "Agendar")
        }
    }
}

