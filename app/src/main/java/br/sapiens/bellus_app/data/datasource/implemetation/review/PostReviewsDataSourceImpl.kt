package br.sapiens.bellus_app.data.datasource.implemetation.review

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PostReviewsDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostReviewsSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseReviewsSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toReviewsDTO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PostReviewsDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PostReviewsDataSource {

    override suspend fun post(schema: PostReviewsSchema): State<ReviewsDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PostReviewsDataSourceImpl", "Token disponível")

                val url =
                    provider.getBaseUrl()
                        .appendPath("reviews/")
                Log.d("PostReviewsDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema)
                }
                Log.d("PostReviewsDataSourceImpl", "Resposta recebida")

                val responseBody: String = response.bodyAsText()
                Log.d(
                    "PostReviewsDataSourceImpl",
                    "Corpo da resposta: $responseBody"
                )

                val reviewSchema = Json.decodeFromString<ResponseReviewsSchema>(responseBody)
                val reviewDTO = reviewSchema.toReviewsDTO()

                State.Success(reviewDTO)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}