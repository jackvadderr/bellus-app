package br.sapiens.bellus_app.data.repository.implemetation.establishment

import br.sapiens.bellus_app.data.datasource.base.PutEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.PutEstablishmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PutEstablishmentRepositoryImpl @Inject constructor(
    private val dataSource: PutEstablishmentDataSource,
) : PutEstablishmentRepository {

    override suspend fun put(schema: PutEstablishmentSchemaEncapsulation): State<EstabelecimentoDTO> {
        return try {
            when (val response: State<EstabelecimentoDTO> =
                dataSource.put(schema)) {
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