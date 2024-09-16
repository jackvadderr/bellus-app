package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

@Composable
fun PortfolioTab() {
    val images = listOf(
        R.mipmap.barbearia,
        R.mipmap.barbearia,
        R.mipmap.barbearia,
    )

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