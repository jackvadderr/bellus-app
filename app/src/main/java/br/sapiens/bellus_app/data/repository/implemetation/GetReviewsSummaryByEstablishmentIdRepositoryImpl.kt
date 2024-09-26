package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetReviewsSummaryByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.data.repository.base.GetReviewsSummaryByEstalishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetReviewsSummaryByEstablishmentIdRepositoryImpl @Inject constructor(
    private val dataSource: GetReviewsSummaryByEstablishmentIdDataSource
) : GetReviewsSummaryByEstalishmentIdRepository {

    override suspend fun getReviewsSummaryByEstablishmentId(id: String): State<ReviewsSummary> {
        return try {
            when (val response = dataSource.getReviewsSummaryByEstablishmentId(id)) {
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