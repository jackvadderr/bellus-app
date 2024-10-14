package br.sapiens.bellus_app.data.repository.implemetation.categories

import br.sapiens.bellus_app.data.datasource.base.GetCategoriesByNumberDataSource
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.data.repository.base.GetCategoriesByNumberRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetCategoriesByNumberRepositoryImpl @Inject constructor(
    private val dataSource: GetCategoriesByNumberDataSource,
) : GetCategoriesByNumberRepository {

    override suspend fun get(id: String): State<List<GetCategoryDTO>> {
        return try {
            when (val response = dataSource.get(id)) {
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