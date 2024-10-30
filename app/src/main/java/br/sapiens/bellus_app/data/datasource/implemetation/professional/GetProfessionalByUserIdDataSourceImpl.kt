package br.sapiens.bellus_app.data.datasource.implemetation.professional

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetProfessionalByUserIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseProfessionalSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toProfessionalDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetProfessionalByUserIdDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetProfessionalByUserIdDataSource {

    override suspend fun get(id: String): State<ProfessionalDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetProfessionalByUserIdDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("profissional/search_by_user/$id")
                Log.d("GetProfessionalByUserIdDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("GetProfessionalByUserIdDataSourceImpl", "Método: GET")
                val response = client.get(url)
                Log.d("GetProfessionalByUserIdDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetProfessionalByUserIdDataSourceImpl", "Corpo da resposta: $responseBody")

                val professionalResponse: ResponseProfessionalSchema =
                    Json.decodeFromString<ResponseProfessionalSchema>(responseBody)
                val professionalDto: ProfessionalDTO = professionalResponse.toProfessionalDTO()

                State.Success(professionalDto)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}