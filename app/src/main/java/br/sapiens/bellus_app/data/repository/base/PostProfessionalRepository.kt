package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.utils.State

fun interface PostProfessionalRepository {
    suspend fun post(schema: PostProfessionalSchema): State<ProfessionalDTO>
}
