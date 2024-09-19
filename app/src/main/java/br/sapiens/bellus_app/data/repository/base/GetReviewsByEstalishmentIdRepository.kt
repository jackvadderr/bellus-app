package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.utils.State

fun interface GetReviewsByEstalishmentIdRepository {
    suspend fun getReviews(id: String): State<List<ReviewsDTO>>
}