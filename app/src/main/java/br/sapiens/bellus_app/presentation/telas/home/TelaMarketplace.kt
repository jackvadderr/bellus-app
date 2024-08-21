package br.sapiens.bellus_app.presentation.telas.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.CategorySection
import br.sapiens.bellus_app.presentation.ui.component.NewInBellusSection
import br.sapiens.bellus_app.presentation.ui.component.SpecialOffersSection
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = "Olá, Usuário",
            style = MaterialTheme.typography.h6,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueNaoSei)
                .padding(16.dp)
        )
        CategorySection()
        SpecialOffersSection()
        NewInBellusSection()
        Spacer(modifier = Modifier.height(16.dp))
    }
}