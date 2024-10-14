package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.utils.State

fun interface GetCategoriesListNameDataSource {
    suspend fun get(): State<List<CategoryNameDTO>>
}