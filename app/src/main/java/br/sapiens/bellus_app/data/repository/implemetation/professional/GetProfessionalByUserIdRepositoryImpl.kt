package br.sapiens.bellus_app.data.repository.implemetation.professional

import br.sapiens.bellus_app.data.datasource.base.GetProfessionalByUserIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.data.repository.base.GetProfessionalByUserIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetProfessionalByUserIdRepositoryImpl @Inject constructor(
    private val dataSource: GetProfessionalByUserIdDataSource,
) : GetProfessionalByUserIdRepository {

    override suspend fun get(id: String): State<ProfessionalDTO> {
        return try {
            when (val response: State<ProfessionalDTO> = dataSource.get(id)) {
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