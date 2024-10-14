package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.utils.State

fun interface GetCategoriesByNumberDataSource {
    suspend fun get(id: String): State<List<GetCategoryDTO>>
}