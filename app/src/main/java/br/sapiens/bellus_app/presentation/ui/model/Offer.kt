package br.sapiens.bellus_app.presentation.ui.model

data class Offer(
    val id: String,
    val title: String,
    val address: String,
    val imageResource: String,
    val rating: Float,
)