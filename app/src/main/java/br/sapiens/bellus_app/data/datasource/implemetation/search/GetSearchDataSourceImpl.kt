package br.sapiens.bellus_app.data.datasource.implemetation.search

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetSearchDataSource
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseSearchSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toSearchDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetSearchDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetSearchDataSource {

    override suspend fun get(query: String): State<List<SearchDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("search/$query")
                Log.d("GetEstablishmentsByIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishmentsSchema: List<ResponseSearchSchema> =
                    Json.decodeFromString<List<ResponseSearchSchema>>(responseBody)

                val establishments = establishmentsSchema.map {
                    it.toSearchDTO()
                }
                Log.d(
                    "GetEstablishmentsByIdDataSourceImpl",
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