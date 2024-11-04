package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostReviewsSchema
import br.sapiens.bellus_app.utils.State

fun interface PostReviewsDataSource {
    suspend fun post(schema: PostReviewsSchema): State<ReviewsDTO>
}