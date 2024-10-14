package br.sapiens.bellus_app.data.repository.implemetation.establishment

import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.GetAllEstablishmentsRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAllEstablishmentsRepositoryImpl @Inject constructor(
    private val getAllEstablishmentsDataSource: GetAllEstablishmentsDataSource,
) : GetAllEstablishmentsRepository {

    override suspend fun getAllEstablishments(): State<List<EstabelecimentoDTO>> {
        return try {
            when (val response = getAllEstablishmentsDataSource.getAll()) {
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