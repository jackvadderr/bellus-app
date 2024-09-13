package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetAllEstablishmentsDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.utils.State
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetAllEstablishmentsDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetAllEstablishmentsDataSource {

    override suspend fun getAllEstablishment(): State<List<EstabelecimentoDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetAllEstablishments", "Token disponível")

                val url = provider.getBaseUrl().appendPath("establishments/")
                Log.d("GetAllEstablishments", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetAllEstablishments", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetAllEstablishments", "Corpo da resposta: $responseBody")

                val establishments: List<EstabelecimentoDTO> = Json.decodeFromString(responseBody)
                Log.d("GetAllEstablishments", "Estabelecimentos decodificados: $establishments")


                State.Success(establishments)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}