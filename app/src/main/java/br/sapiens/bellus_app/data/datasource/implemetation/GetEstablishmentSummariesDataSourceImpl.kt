package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentsSummaryDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetEstablishmentSummariesDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetEstablishmentsSummaryDataSource {

    override suspend fun getSummaries(): State<List<EstabelecimentoSummaryDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetEstablishmentSummariesDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("establishments/summary/")
                Log.d("GetEstablishmentSummariesDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetEstablishmentSummariesDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetEstablishmentSummariesDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishments: List<EstabelecimentoSummaryDTO> =
                    Json.decodeFromString(responseBody)
                Log.d(
                    "GetEstablishmentSummariesDataSourceImpl",
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