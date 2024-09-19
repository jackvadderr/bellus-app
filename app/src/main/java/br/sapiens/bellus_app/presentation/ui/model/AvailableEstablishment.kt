package br.sapiens.bellus_app.presentation.ui.model

// AQUI SERVE APENAS PARA A TELA INICIAL DO MARKETPLACE, NÃO PENSE EM UTILIZAR NOS DETALHES
data class AvailableEstablishment(
    val id: String,
    val name: String,
    val address: String,
    val imageResource: String,
    val rating: Float,
)


