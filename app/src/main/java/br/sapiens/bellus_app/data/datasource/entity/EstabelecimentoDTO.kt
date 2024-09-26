package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EstabelecimentoDTO(
    val id: String,
//    val profissional_ids: List<String>, // TODO: Ainda não resolvemos essa questão
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val nome: String,
    val cnpj: String,
    val rating: Float,
    val endereco: EnderecoPartialModel,
    val telefone: List<String>,
    val horario_funcionamento: HorarioFuncionamento,
    val imagem: List<String>,
    val portfolio: List<String>,
    val description: String,
    val reviews_id: String,
)

@Serializable
data class HorarioFuncionamento(
    val segunda_feira: Horario?,
    val terca_feira: Horario?,
    val quarta_feira: Horario?,
    val quinta_feira: Horario?,
    val sexta_feira: Horario?,
    val sabado: Horario?,
    val domingo: Horario?,
)

@Serializable
data class Horario(
    val abertura: String,
    val fechamento: String,
)