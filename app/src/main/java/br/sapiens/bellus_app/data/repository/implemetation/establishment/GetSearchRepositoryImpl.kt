package br.sapiens.bellus_app.data.repository.implemetation.establishment

import br.sapiens.bellus_app.data.datasource.base.GetSearchDataSource
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.data.repository.base.GetSearchRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetSearchRepositoryImpl @Inject constructor(
    private val dataSource: GetSearchDataSource
) : GetSearchRepository {

    override suspend fun get(query: String): State<List<SearchDTO>> {
        return try {
            when (val response = dataSource.get(query)) {
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