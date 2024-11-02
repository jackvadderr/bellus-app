package br.sapiens.bellus_app.presentation.ui.model

import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento

data class EstablishmentDetail(
    val id: String,
    val name: String,
    val address: EnderecoPartialModel,
    val telefone: List<String>,
    val rating: Float,
    val imageResource: List<String>,
    val portfolio: List<String>,
    val horario_funcionamento: HorarioFuncionamento,
    val description: String,
    val totalReviews: Int,
    val cnjp: String,
    val profissionais_filiados: List<String>,
    val profisisonal_dono: String,
)
