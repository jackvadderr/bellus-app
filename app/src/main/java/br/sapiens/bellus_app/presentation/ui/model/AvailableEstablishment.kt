package br.sapiens.bellus_app.presentation.ui.model

import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel

// AQUI SERVE APENAS PARA A TELA INICIAL DO MARKETPLACE, NÃO PENSE EM UTILIZAR NOS DETALHES
data class AvailableEstablishment(
    val id: String,
    val name: String,
    val address: EnderecoPartialModel,
    val imageResource: String,
    val rating: Float,
)