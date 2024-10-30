package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import kotlinx.serialization.Serializable

@Serializable
data class PostEstablishmentSchema(
    val cnpj: String,
    val nome: String,
    val endereco: EnderecoPartialModel,
    val telefone: List<String>,
    val horario_funcionamento: HorarioFuncionamento,
    val imagem: List<String>,
    val portfolio: List<String>,
    val description: String,
    val profissionais_filiados: List<String>,
    val profissional_dono: String,
)

//{
//    "cnpj": "86377193000110",
//    "nome": "Bellus",
//    "endereco": {
//    "rua": "Rua do Strogonoff de Tatu",
//    "numero": "1234",
//    "estado": "RO",
//    "cidade": "Porto Velho",
//    "cep": "12345678"
//},
//    "telefone": ["999999999"],
//    "horario_funcionamento": {
//    "segunda_feira": {"abertura": "08:30", "fechamento": "18:00"},
//    "terca_feira": {"abertura": "08:30", "fechamento": "18:00"},
//    "quarta_feira": {"abertura": "08:30", "fechamento": "18:00"}
//},
//    "imagem": ["https://firebasestorage.googleapis.com/v0/b/bellus-app-48854.appspot.com/o/barbearia.webp?alt=media&token=0d6b1dec-4dad-4202-8f82-4e956f6de005"],
//    "portfolio": ["https://firebasestorage.googleapis.com/v0/b/bellus-app-48854.appspot.com/o/barbearia.webp?alt=media&token=0d6b1dec-4dad-4202-8f82-4e956f6de005"],
//    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra lectus et eros placerat tempus. In maximus imperdiet rhoncus. In hac habitasse platea dictumst.",
//    "profissionais_filiados": [""],
//    "profissional_dono": "exemplo"
//}