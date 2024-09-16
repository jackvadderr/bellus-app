package br.sapiens.bellus_app.presentation.ui.model

data class NewItemModel(
    val id: String,
    val name: String,
    val location: String,
    val imageResource: String,
    val rating: Float,
)