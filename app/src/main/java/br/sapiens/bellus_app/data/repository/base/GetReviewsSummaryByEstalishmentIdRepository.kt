package br.sapiens.bellus_app.data.repository.base

import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.utils.State

fun interface GetReviewsSummaryByEstalishmentIdRepository {
    suspend fun getReviewsSummaryByEstablishmentId(id: String): State<ReviewsSummary>
}