package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.utils.State

fun interface DeleteServiceRepository {
    suspend fun delete(id: String): State<DeleteDTO>
}
