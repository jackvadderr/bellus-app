package br.sapiens.bellus_app.presentation.telas.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.sections.CategorySection
import br.sapiens.bellus_app.presentation.ui.component.sections.NewInBellusSection
import br.sapiens.bellus_app.presentation.ui.component.sections.SpecialOffersSection
import br.sapiens.bellus_app.presentation.ui.model.CategoryModel
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel
import br.sapiens.bellus_app.presentation.ui.model.Offer
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel

@Composable
fun TelaMarketplace(
    viewModel: MarketplaceViewModel,
) {
    val scrollState = rememberScrollState()
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.triggerEvent(MarketplaceViewModel.ViewEvent.LoadUser)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
//            .padding(bottom = 30.dp, top = 60.dp)
    ) {
        Text(
            text = "Olá, Usuário",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueNaoSei)
                .padding(16.dp)
        )
        when (viewState) {
            is MarketplaceViewModel.ViewState.Loading -> {
                Log.d("TelaMarketplace", "ViewState is Loading")
            }

            is MarketplaceViewModel.ViewState.UserLoaded -> {
                Log.d("TelaMarketplace", "ViewState is UserLoaded")
                val categories: List<CategoryModel> =
                    (viewState as MarketplaceViewModel.ViewState.UserLoaded).categories
                val myItems: List<NewItemModel> =
                    (viewState as MarketplaceViewModel.ViewState.UserLoaded).newItems
                val myOffers: List<Offer> =
                    (viewState as MarketplaceViewModel.ViewState.UserLoaded).offers

                CategorySection(categories)
                SpecialOffersSection(myOffers)
                NewInBellusSection(myItems)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}