package br.sapiens.bellus_app.data.repository.implemetation.establishment

import br.sapiens.bellus_app.data.datasource.base.PostEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.base.PostEstablishmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostEstablishmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostEstablishmentRepositoryImpl @Inject constructor(
    private val dataSource: PostEstablishmentDataSource,
) : PostEstablishmentRepository {

    override suspend fun post(schema: PostEstablishmentSchema): State<EstabelecimentoDTO> {
        return try {
            when (val response: State<EstabelecimentoDTO> =
                dataSource.post(schema)) {
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