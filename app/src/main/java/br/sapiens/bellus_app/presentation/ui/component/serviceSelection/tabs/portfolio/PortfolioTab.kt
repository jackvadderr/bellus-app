package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.portfolio

import CustomButton
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.EstadoEnum
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import java.util.UUID

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
fun PortfolioTabParceiro(
    establishments: EstablishmentDetail,
    openGallery: () -> Unit,
//    toUpdate: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val images = establishments.portfolio

    CreateNewImagePortfolio(
        navigate = {
            Log.d("PortfolioTabParceiro", "Botão de adicionar imagem clicado")
            openGallery()
//            toUpdate()
        }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(images) { _, imageRes ->
            PortfolioImage(imageRes)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


fun uploadImageToPortfolio(
    imageUri: String,
    onAddImageClick: (EstablishmentDetail) -> Unit
) {
    val storagePath = "portfolio/${UUID.randomUUID()}.jpg"
    // Atualiza o EstablishmentDetail com o URL da imagem carregada
    val updatedEstablishmentDetail = EstablishmentDetail(
        id = "",
        cnjp = "",
        name = "",
        address = EnderecoPartialModel(
            rua = "",
            numero = "",
            cidade = "",
            estado = EstadoEnum.RO,
            cep = ""
        ),
        telefone = emptyList(),
        horario_funcionamento = HorarioFuncionamento(
            segunda_feira = Horario(
                abertura = "",
                fechamento = ""
            ),
            terca_feira = Horario(
                abertura = "",
                fechamento = ""
            ),
            quarta_feira = Horario(
                abertura = "",
                fechamento = ""
            ),
            quinta_feira = Horario(
                abertura = "",
                fechamento = ""
            ),
            sexta_feira = Horario(
                abertura = "",
                fechamento = ""
            ),
            sabado = Horario(
                abertura = "",
                fechamento = ""
            ),
            domingo = Horario(
                abertura = "",
                fechamento = ""
            )
        ),
        rating = 0.0f,
        imageResource = listOf(imageUri),
        portfolio = listOf(imageUri),
        description = "",
        profisisonal_dono = "",
        profissionais_filiados = emptyList(),
        totalReviews = 0,
    )
    onAddImageClick(updatedEstablishmentDetail)
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