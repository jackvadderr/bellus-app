package br.sapiens.bellus_app.dominio.usecase.categories

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.data.repository.base.GetCategoriesByNumberRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetCategoriesByNumberUseCase @Inject constructor(
    private val repository: GetCategoriesByNumberRepository
) : UseCase<String, List<GetCategoryDTO>>() {
    public override suspend fun invoke(input: String?): State<List<GetCategoryDTO>> {
        return try {
            when (val response: State<List<GetCategoryDTO>> = repository.get(input!!)) {
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