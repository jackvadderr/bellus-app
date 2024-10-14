package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ResponseEstablishment(
    val id: String,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val nome: String,
    val cnpj: String,
    val average_rating: Float,
    val endereco: EnderecoPartialModel,
    val telefone: List<String>,
    val horario_funcionamento: HorarioFuncionamento,
    val imagem: List<String>,
    val portfolio: List<String>,
    val description: String,
    val reviews_id: String,
)