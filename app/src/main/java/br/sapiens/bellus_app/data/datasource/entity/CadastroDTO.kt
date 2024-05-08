package br.sapiens.bellus_app.data.datasource.entity

import br.sapiens.bellus_app.data.repository.model.GeneroEnum

data class CadastroDTO(
    val name: String? = null,
    val phone: String? = null,
    val genero: GeneroEnum? = null,
    val email: String?,
)