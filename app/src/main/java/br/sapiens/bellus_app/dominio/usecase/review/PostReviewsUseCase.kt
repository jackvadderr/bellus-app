package br.sapiens.bellus_app.dominio.usecase.review

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.repository.base.PostReviewsRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostReviewsSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostReviewsUseCase @Inject constructor(
    private val repository: PostReviewsRepository
) : UseCase<PostReviewsSchema, ReviewsDTO>() {
    public override suspend fun invoke(input: PostReviewsSchema?): State<ReviewsDTO> {
        return try {
            when (val response: State<ReviewsDTO> =
                repository.post(input!!)) {
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