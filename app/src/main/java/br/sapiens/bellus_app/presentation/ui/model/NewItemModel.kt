package br.sapiens.bellus_app.presentation.ui.model

data class NewItemModel(
    val name: String,
    val location: String,
    val imageResource: String,
    val rating: Any = 4.5
)