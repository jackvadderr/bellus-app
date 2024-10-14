package br.sapiens.bellus_app.data.datasource.implemetation.categories

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetCategoriesListNameDataSource
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.dominio.sdk.network.KtorClientProvider
import br.sapiens.bellus_app.dominio.sdk.network.appendPath
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategoryNameSchema
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toCategoryNameDTO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetCategoriesListNameDataSourceImpl @Inject constructor(
    private val provider: KtorClientProvider
) : GetCategoriesListNameDataSource {

    override suspend fun get(): State<List<CategoryNameDTO>> {
        return if (provider.isTokenAvailable()) {
            try {
                Log.d("GetCategoriesListNameDataSourceImpl", "Token disponível")
                val url = provider.getBaseUrl()
                    .appendPath("categories/categories")
                Log.d("GetCategoriesListNameDataSourceImpl", "URL: $url")

                val client = provider.client
                Log.d("GetCategoriesListNameDataSourceImpl", "Método: GET")
                val response = client.get(url)
                Log.d("GetCategoriesListNameDataSourceImpl", "Resposta recebida")

                val responseBody = response.bodyAsText()
                Log.d("GetCategoriesListNameDataSourceImpl", "Corpo da resposta: $responseBody")

                val categoriesResponse: List<ResponseCategoryNameSchema> =
                    Json.decodeFromString<List<ResponseCategoryNameSchema>>(responseBody)
                val categories: List<CategoryNameDTO> = categoriesResponse.map {
                    it.toCategoryNameDTO()
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