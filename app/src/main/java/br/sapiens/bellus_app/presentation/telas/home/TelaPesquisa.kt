package br.sapiens.bellus_app.presentation.telas.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.CustomSearchBar
import br.sapiens.bellus_app.presentation.ui.component.sections.CategoriesList
import br.sapiens.bellus_app.presentation.viewmodels.PesquisaViewModel

@Composable
fun TelaPesquisa(
    viewModel: PesquisaViewModel,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    Column(modifier = Modifier.padding(top = 0.dp)) {
        CustomSearchBar()
        CategoriesList()
    }
}

@Preview
@Composable
fun PreviewTelaPesquisa() {
    val mockViewModel = object : PesquisaViewModel() {
        // Implement necessary properties and methods for the mock
    }
    TelaPesquisa(viewModel = mockViewModel, navigateToSearch = {}, navigateToProfile = {})
}