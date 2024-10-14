package br.sapiens.bellus_app.data.repository.implemetation.categories

import br.sapiens.bellus_app.data.datasource.base.GetCategoriesListNameDataSource
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.data.repository.base.GetCategoriesListNameRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetCategoriesListNameRepositoryImpl @Inject constructor(
    private val dataSource: GetCategoriesListNameDataSource,
) : GetCategoriesListNameRepository {

    override suspend fun get(): State<List<CategoryNameDTO>> {
        return try {
            when (val response = dataSource.get()) {
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