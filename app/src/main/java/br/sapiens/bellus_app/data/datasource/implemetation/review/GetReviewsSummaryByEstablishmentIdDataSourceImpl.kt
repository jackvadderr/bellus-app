package br.sapiens.bellus_app.data.datasource.implemetation.review

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetReviewsSummaryByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsSummary
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetReviewsSummaryByEstablishmentIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetReviewsSummaryByEstablishmentIdDataSource {

    override suspend fun getReviewsSummaryByEstablishmentId(id: String): State<ReviewsSummary> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetReviewsSummaryByEstablishmentIdDataSourceImpl", "Token disponível")

                val url =
                    provider.getBaseUrl()
                        .appendPath("establishments/reviews/${id}/summary")
                Log.d("GetReviewsSummaryByEstablishmentIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetReviewsSummaryByEstablishmentIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d(
                    "GetReviewsSummaryByEstablishmentIdDataSourceImpl",
                    "Corpo da resposta: $responseBody"
                )

                val reviewSummary: ReviewsSummary = Json.decodeFromString(responseBody)

                State.Success(reviewSummary)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}