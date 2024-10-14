package br.sapiens.bellus_app.dominio.usecase.search

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.data.repository.base.GetSearchRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val repository: GetSearchRepository,
) : UseCase<String, List<SearchDTO>>() {
    public override suspend fun invoke(input: String?): State<List<SearchDTO>> {
        return try {
            when (val response = repository.get(input!!)) {
                is State.Success -> response
                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}