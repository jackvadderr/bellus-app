package br.sapiens.bellus_app.data.repository.implemetation.review

import br.sapiens.bellus_app.data.datasource.base.PostReviewsDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.repository.base.PostReviewsRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostReviewsSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostReviewsRepositoryImpl @Inject constructor(
    private val dataSource: PostReviewsDataSource
) : PostReviewsRepository {

    override suspend fun post(schema: PostReviewsSchema): State<ReviewsDTO> {
        return try {
            when (val response = dataSource.post(schema)) {
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