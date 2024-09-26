package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.utils.State

fun interface GetReviewsSummaryByEstablishmentIdDataSource {
    suspend fun getReviewsSummaryByEstablishmentId(id: String): State<ReviewsSummary>
}