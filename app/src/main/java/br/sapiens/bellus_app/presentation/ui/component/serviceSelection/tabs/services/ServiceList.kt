package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

@Composable
fun ServiceList(items: List<ServiceDetails>) {
    Column {
        items.forEach { item ->
            ServiceItem(item)
            HorizontalDivider()
        }
    }
}