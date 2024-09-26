package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetReviewsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetReviewsByEstablishmentIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetReviewsByEstablishmentIdDataSource {

    override suspend fun getReviewsById(establishmentId: String): State<List<ReviewsDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetReviewsByEstablishmentIdDataSourceImpl", "Token disponível")

                val url =
                    provider.getBaseUrl().appendPath("establishments/reviews/${establishmentId}")
                Log.d("GetReviewsByEstablishmentIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetReviewsByEstablishmentIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d(
                    "GetReviewsByEstablishmentIdDataSourceImpl",
                    "Corpo da resposta: $responseBody"
                )

                val establishments: List<ReviewsDTO> =
                    Json.decodeFromString(responseBody)
                Log.d(
                    "GetReviewsByEstablishmentIdDataSourceImpl",
                    "Estabelecimentos decodificados: $establishments"
                )


                State.Success(establishments)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}