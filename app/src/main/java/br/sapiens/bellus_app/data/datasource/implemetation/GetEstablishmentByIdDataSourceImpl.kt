package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetEstablishmentByIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetEstablishmentByIdDataSource {

    override suspend fun getEstablishmentById(id: String): State<EstabelecimentoDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Token disponível")

                val url = provider.getBaseUrl().appendPath("establishments/${id}")
                Log.d("GetEstablishmentsByIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishments: EstabelecimentoDTO = Json.decodeFromString(responseBody)
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