package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.utils.State

fun interface GetProfessionalByUserIdRepository {
    suspend fun get(id: String): State<ProfessionalDTO>
}
