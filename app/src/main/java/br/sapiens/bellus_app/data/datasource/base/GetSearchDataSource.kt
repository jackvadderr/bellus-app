package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.utils.State

fun interface GetSearchDataSource {
    suspend fun get(query: String): State<List<SearchDTO>>
}