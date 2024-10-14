package br.sapiens.bellus_app.dominio.usecase.review

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetReviewsByEstablishmentIdUseCase @Inject constructor(
    private val repository: GetReviewsByEstalishmentIdRepository
) : UseCase<String, List<ReviewsDTO>>() {
    public override suspend fun invoke(input: String?): State<List<ReviewsDTO>> {
        return try {
            when (val response: State<List<ReviewsDTO>> =
                repository.getReviews(input!!)) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }
}