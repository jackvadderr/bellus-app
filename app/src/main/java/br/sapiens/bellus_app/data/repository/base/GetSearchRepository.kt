package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.utils.State

fun interface GetSearchRepository {
    suspend fun get(query: String): State<List<SearchDTO>>
}
