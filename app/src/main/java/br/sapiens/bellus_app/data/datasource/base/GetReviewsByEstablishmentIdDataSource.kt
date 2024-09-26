package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.utils.State

fun interface GetReviewsByEstablishmentIdDataSource {
    suspend fun getReviewsById(id: String): State<List<ReviewsDTO>>
}