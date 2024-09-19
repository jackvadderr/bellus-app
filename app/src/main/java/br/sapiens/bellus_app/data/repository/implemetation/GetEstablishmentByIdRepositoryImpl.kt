package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.GetEstablishmentByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetEstablishmentByIdRepositoryImpl @Inject constructor(
    private val dataSource: GetEstablishmentByIdDataSource
) : GetEstablishmentByIdRepository {

    override suspend fun getEstablishmentById(id: String): State<EstabelecimentoDTO> {
        return try {
            when (val response = dataSource.getEstablishmentById(id)) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}