package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.utils.State

fun interface GetCategoriesListNameRepository {
    suspend fun get(): State<List<CategoryNameDTO>>
}
