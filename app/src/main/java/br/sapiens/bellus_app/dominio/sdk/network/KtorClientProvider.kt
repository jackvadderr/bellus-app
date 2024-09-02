package br.sapiens.bellus_app.dominio.sdk.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.serialization.kotlinx.json.json

import kotlinx.serialization.json.Json

object KtorClientProvider {
    private const val BASE_URL = "http://0.0.0.0:8080/api/v1/"
    private var token: String? = null

    val client: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
        install(Auth) {
            bearer {
                loadTokens {
                    // Load tokens from a local storage and return them as the 'BearerTokens' instance
                    BearerTokens(token?:"", "")
                }
            }
        }

    }

    fun getBaseUrl(): String = BASE_URL

    fun setToken(newToken: String) {
        token = newToken
    }
}

fun String.appendPath(path: String): String {
    return if (this.endsWith("/")) {
        this + path.removePrefix("/")
    } else {
        "$this/$path"
    }
}