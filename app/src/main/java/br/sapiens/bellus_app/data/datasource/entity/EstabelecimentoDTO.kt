package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class EstabelecimentoDTO(
    val id: String,
    val profissional_ids: List<String>,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val nome: String,
    val cnpj: String,
    val endereco: String,
    val telefone: String,
    val horario_funcionamento: String,
    val imagem: List<String>,
    val portfolio: List<String>
)