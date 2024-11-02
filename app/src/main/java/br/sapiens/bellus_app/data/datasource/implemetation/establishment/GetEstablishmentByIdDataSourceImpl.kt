package br.sapiens.bellus_app.data.datasource.implemetation.establishment

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetEstablishmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseEstablishmentSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDTO
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

                val url = provider.getBaseUrl().appendPath("establishments/by/${id}")
                Log.d("GetEstablishmentsByIdDataSourceImpl", "URL: $url")

                val client = provider.client
                val response = client.get(url)
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Resposta recebida")

                val responseBody: String = response.bodyAsText()
//                    .replace("[", "").replace("]", "")
                Log.d("GetEstablishmentsByIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val establishment: ResponseEstablishmentSchema = Json.decodeFromString(responseBody)

                // Log the deserialized fields
                Log.d("GetEstablishmentsByIdDataSourceImpl", "ID: ${establishment.id}")
                Log.d(
                    "GetEstablishmentsByIdDataSourceImpl",
                    "CreateAt: ${establishment.created_at}"
                )
                Log.d(
                    "GetEstablishmentsByIdDataSourceImpl",
                    "UpdateAt: ${establishment.updated_at}"
                )
                // Add more fields as needed

                val establishmentDto: EstabelecimentoDTO = establishment.toEstablishmentDTO()
                Log.d(
                    "GetEstablishmentsByIdDataSourceImpl",
                    "Estabelecimentos decodificados: $establishment"
                )

                State.Success(establishmentDto)
            } catch (exception: Exception) {
                Log.e("GetEstablishmentsByIdDataSourceImpl", "Erro ao decodificar JSON", exception)
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}