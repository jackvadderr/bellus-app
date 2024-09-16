package br.sapiens.bellus_app.data.datasource.entity

import br.sapiens.bellus_app.presentation.ui.model.Duration

data class ServiceDTO(
    val id: String,
    val name: String,
    val description: String,
    val duration: Duration,
    val price: Float,
    val establishment_id: String
)
