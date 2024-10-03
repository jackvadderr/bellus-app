package br.sapiens.bellus_app.presentation.ui.model

import kotlinx.serialization.Serializable

data class ServiceDetails(
    val id: String,
    val name: String,
    val duration: Duration,
    val preco: Float,
)

data class ServicePost(
    val name: String,
    val duration: Duration,
    val preco: Float,
)

@Serializable
data class Duration(
    val type: String,
    val value: Float,
)