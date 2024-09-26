package br.sapiens.bellus_app.data.datasource.implemetation

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseEstablishment
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

                val establishments: ResponseEstablishment =
                    Json.decodeFromString<ResponseEstablishment>(responseBody)

                // TODO: Lembrar fazer um Mapper XD
                val establishmentDto: EstabelecimentoDTO = EstabelecimentoDTO(
                    establishments.id,
                    establishments.created_at,
                    establishments.updated_at,
                    establishments.nome,
                    establishments.cnpj,
                    establishments.rating,
                    establishments.endereco,
                    establishments.telefone,
                    establishments.horario_funcionamento,
                    establishments.imagem,
                    establishments.portfolio,
                    establishments.description,
                    establishments.reviews_id
                )
                Log.d(
                    "GetEstablishmentsByIdDataSourceImpl",
                    "Estabelecimentos decodificados: $establishments"
                )


                State.Success(establishmentDto)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}