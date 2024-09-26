package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.data.repository.base.GetReviewsSummaryByEstalishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetReviewsSummaryByEstablishmentIdUseCase @Inject constructor(
    private val repository: GetReviewsSummaryByEstalishmentIdRepository
) : UseCase<String, ReviewsSummary>() {
    public override suspend fun invoke(input: String?): State<ReviewsSummary> {
        return try {
            when (val response: State<ReviewsSummary> =
                repository.getReviewsSummaryByEstablishmentId(input!!)) {
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