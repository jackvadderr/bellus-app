package br.sapiens.bellus_app.presentation.telas.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    Text("TELA DO MARKETPLACE AMIGÃO")
}