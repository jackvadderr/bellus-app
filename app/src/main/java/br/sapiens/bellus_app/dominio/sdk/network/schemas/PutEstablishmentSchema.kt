package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import kotlinx.serialization.Serializable

@Serializable
data class PutEstablishmentSchema(
    val cnpj: String?,
    val nome: String?,
    val endereco: EnderecoPartialModel?,
    val telefone: List<String>?,
    val horario_funcionamento: HorarioFuncionamento?,
    val imagem: List<String>?,
    val portfolio: List<String>?,
    val description: String?,
    val profissionais_filiados: List<String>?,
    val profissional_dono: String?,
)

data class PutEstablishmentSchemaEncapsulation(
    val id: String,
    val schema: PutEstablishmentSchema
)