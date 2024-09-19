package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.serialization.Serializable

@Serializable
data class EstabelecimentoSummaryDTO(
    val id: String,
    val nome: String,
    val endereco: String,
    val rating: Float,
    val imagem: List<String>
)