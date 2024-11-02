package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio

import CustomButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

@Composable
fun PortfolioTabParceiro(images: List<String>) {
// TODO: Aqui vai dá trabalhinho porque vamos ter que utilizar Firebase Storage para obter o link
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
//        images.forEach { imageRes ->
//            PortfolioImage(imageRes)
//            Spacer(modifier = Modifier.height(16.dp))
//        }
        itemsIndexed(images) { index, imageRes ->
            PortfolioImage(imageRes)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            CreateNewImagePortfolio(
                navigate = {
//            currentService.value = null
//            showDialog.value = true
                }
            )
        }

    }
    // TODO: Vamos redirecionar para uma nova tela e FDS

}

@Composable
fun CreateNewImagePortfolio(
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navigate() }
    ) {
        CustomButton(
            onClick = navigate,
            texto = "Adicionar nova imagem"
        )
    }
}