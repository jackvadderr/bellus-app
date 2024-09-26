package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstabelecimentoSummaryDTO(
    val id: String,
    val nome: String,
    val endereco: EnderecoPartialModel,
    val rating: Float,
    val imagem: List<String>
)

@Serializable
data class EnderecoPartialModel(
    val rua: String,
    val numero: String,
    val cidade: String,
    val estado: EstadoEnum,
    val cep: String,
)

@Serializable
enum class EstadoEnum(val description: String) {

    @SerialName("AC")
    AC("Acre"),

    @SerialName("AL")
    AL("Alagoas"),

    @SerialName("AP")
    AP("Amapá"),

    @SerialName("AM")
    AM("Amazonas"),

    @SerialName("BA")
    BA("Bahia"),

    @SerialName("CE")
    CE("Ceará"),

    @SerialName("DF")
    DF("Distrito Federal"),

    @SerialName("ES")
    ES("Espírito Santo"),

    @SerialName("GO")
    GO("Goiás"),

    @SerialName("MA")
    MA("Maranhão"),

    @SerialName("MT")
    MT("Mato Grosso"),

    @SerialName("MS")
    MS("Mato Grosso do Sul"),

    @SerialName("MG")
    MG("Minas Gerais"),

    @SerialName("PA")
    PA("Pará"),

    @SerialName("PB")
    PB("Paraíba"),

    @SerialName("PR")
    PR("Paraná"),

    @SerialName("PE")
    PE("Pernambuco"),

    @SerialName("PI")
    PI("Piauí"),

    @SerialName("RJ")
    RJ("Rio de Janeiro"),

    @SerialName("RN")
    RN("Rio Grande do Norte"),

    @SerialName("RS")
    RS("Rio Grande do Sul"),

    @SerialName("RO")
    RO("Rondônia"),

    @SerialName("RR")
    RR("Roraima"),

    @SerialName("SC")
    SC("Santa Catarina"),

    @SerialName("SP")
    SP("São Paulo"),

    @SerialName("SE")
    SE("Sergipe"),

    @SerialName("TO")
    TO("Tocantins");
}