package br.sapiens.bellus_app.presentation.ui.model

data class Review( // TODO: Apagar essa porcaria de modelo
    val username: String,
    val date: String,
    val rating: Int,
    val comment: String
)