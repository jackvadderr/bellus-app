package br.sapiens.bellus_app.data.datasource.implemetation.professional

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.PostProfessionalDataSource
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostProfessionalSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseProfessionalSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toProfessionalDTO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PostProfessionaldDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : PostProfessionalDataSource {

    override suspend fun post(schema: PostProfessionalSchema): State<ProfessionalDTO> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("PostProfessionaldDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("profissional/")
                Log.d("PostProfessionaldDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("PostProfessionaldDataSourceImpl", "Método: GET")
                val response = client.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(schema)
                }
                Log.d("PostProfessionaldDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("PostProfessionaldDataSourceImpl", "Corpo da resposta: $responseBody")

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