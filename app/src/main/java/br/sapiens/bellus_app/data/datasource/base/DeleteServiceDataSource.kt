package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.utils.State

fun interface DeleteServiceDataSource {
    suspend fun delete(id: String): State<DeleteDTO>
}