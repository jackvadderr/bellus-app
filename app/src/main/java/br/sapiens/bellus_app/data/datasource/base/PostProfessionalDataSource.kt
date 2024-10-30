package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.utils.State

fun interface PostProfessionalDataSource {
    suspend fun post(schema: PostProfessionalSchema): State<ProfessionalDTO>
}