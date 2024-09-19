package br.sapiens.bellus_app.presentation.ui.model

import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento

data class EstablishmentDetail(
    val id: String,
    val name: String,
    val address: String,
    val telefone: List<String>,
    val rating: Float,
    val imageResource: String,
    val portfolio: String,
    val horario_funcionamento: HorarioFuncionamento,
    val reviews_id: String, // TODO: Não preciso desse campo
)
