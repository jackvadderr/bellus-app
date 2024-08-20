package br.sapiens.bellus_app.presentation.telas.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.CategorySection
import br.sapiens.bellus_app.presentation.ui.component.NewInBellusSection
import br.sapiens.bellus_app.presentation.ui.component.SpecialOffersSection
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        CategorySection()
        SpecialOffersSection()
        NewInBellusSection()
    }
}