package br.sapiens.bellus_app.dominio.usecase.categories

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.data.repository.base.GetCategoriesListNameRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetCategoriesListNameUseCase @Inject constructor(
    private val repository: GetCategoriesListNameRepository
) : UseCase<Nothing, List<CategoryNameDTO>>() {
    public override suspend fun invoke(input: Nothing?): State<List<CategoryNameDTO>> {
        return try {
            when (val response: State<List<CategoryNameDTO>> = repository.get()) {
                is State.Success -> {
                    val responseAppointment = response.data
                    State.Success(responseAppointment)
                }

                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}