package br.sapiens.bellus_app.data.repository.implemetation.professional

import br.sapiens.bellus_app.data.datasource.base.PostProfessionalDataSource
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.data.repository.base.PostProfessionalRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostProfessionalRepositoryImpl @Inject constructor(
    private val dataSource: PostProfessionalDataSource,
) : PostProfessionalRepository {

    override suspend fun post(schema: PostProfessionalSchema): State<ProfessionalDTO> {
        return try {
            when (val response: State<ProfessionalDTO> = dataSource.post(schema)) {
                is State.Success -> {
                    val categories = response.data
                    State.Success(categories)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}