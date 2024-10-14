package br.sapiens.bellus_app.presentation.telas.pesquisa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.component.CustomSearchBar
import br.sapiens.bellus_app.presentation.ui.component.WhyDontFuckingLoading
import br.sapiens.bellus_app.presentation.ui.component.sections.CategoriesList
import br.sapiens.bellus_app.presentation.ui.component.sections.newEstablishments.CardEstablishments
import br.sapiens.bellus_app.presentation.viewmodels.PesquisaViewModel
import br.sapiens.bellus_app.utils.toAvailableEstablishment
import br.sapiens.bellus_app.utils.toCategory
import kotlinx.coroutines.launch

@Composable
fun TelaPesquisa(
    viewModel: PesquisaViewModel,
    navigateToCategories: () -> Unit,
    navigateToEstablishmentDetails: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(top = 0.dp)) {
        CustomSearchBar(
            onSearch = { query ->
                viewModel.scope.launch {
                    viewModel.getEstablishment(query)
                }
            }
        )
        when (viewState) {
            PesquisaViewModel.ViewState.Loading -> {
                WhyDontFuckingLoading()
            }

            is PesquisaViewModel.ViewState.LoadCategoriesList -> {
                val categories =
                    (viewState as PesquisaViewModel.ViewState.LoadCategoriesList).categories
                CategoriesList(
                    categories = categories.map { it.toCategory() },
                    modifier = Modifier,
                    marketplaceStore = viewModel.mkt,
                    navigate = navigateToCategories
                )

            }

            is PesquisaViewModel.ViewState.LoadCategories -> {}
            is PesquisaViewModel.ViewState.SearchResults -> {
                val searchResults: List<EstabelecimentoDTO> =
                    (viewState as PesquisaViewModel.ViewState.SearchResults).results
                SearchResultsList(
                    results = searchResults,
                    mkt = viewModel.mkt,
                    navigate = navigateToEstablishmentDetails,
                )
            }
        }

    }
}


@Composable
fun SearchResultsList(
    results: List<EstabelecimentoDTO>,
    navigate: () -> Unit,
    mkt: MarketplaceStore
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(results.map { it.toAvailableEstablishment() }) { establishment ->
            CardEstablishments(
                navigateToDetails = { navigate() },
                item = establishment,
                marketplaceStore = mkt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}