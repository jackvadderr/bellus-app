package br.sapiens.bellus_app.data.datasource.implemetation.categories

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesByNumberDataSource
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategorySchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toCategoryDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetCategoriesByNumberDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetCategoriesByNumberDataSource {

    override suspend fun get(id: String): State<List<GetCategoryDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetCategoriesByNumberDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("categories/by/${id}")
                Log.d("GetCategoriesByNumberDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("GetCategoriesByNumberDataSourceImpl", "Método: GET")
                val response = client.get(url)
                Log.d("GetCategoriesByNumberDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetCategoriesByNumberDataSourceImpl", "Corpo da resposta: $responseBody")

                val categoriesResponse: List<ResponseCategorySchema> =
                    Json.decodeFromString<List<ResponseCategorySchema>>(responseBody)
                val categories: List<GetCategoryDTO> = categoriesResponse.map {
                    it.toCategoryDTO()
                }
                State.Success(categories)
            } catch (exception: Exception) {
                State.Error(exception)
            }
        } else {
            State.Error(Exception("Token não disponível"))
        }
    }
}