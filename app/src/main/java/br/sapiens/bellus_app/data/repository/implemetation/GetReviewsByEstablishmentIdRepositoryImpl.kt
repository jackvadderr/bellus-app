package br.sapiens.bellus_app.data.repository.implemetation

import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.repository.base.GetReviewsByEstalishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetReviewsByEstablishmentIdRepositoryImpl @Inject constructor(
    private val dataSource: GetReviewsByEstablishmentIdDataSource
) : GetReviewsByEstalishmentIdRepository {

    override suspend fun getReviews(id: String): State<List<ReviewsDTO>> {
        return try {
            when (val response = dataSource.getReviewsById(id)) {
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