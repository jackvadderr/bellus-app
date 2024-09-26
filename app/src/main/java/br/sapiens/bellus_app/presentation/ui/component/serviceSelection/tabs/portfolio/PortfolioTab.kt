package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PortfolioTab(images: List<String>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        images.forEach { imageRes ->
            PortfolioImage(imageRes)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}