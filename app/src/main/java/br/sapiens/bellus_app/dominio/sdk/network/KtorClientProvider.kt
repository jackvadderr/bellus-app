package br.sapiens.bellus_app.dominio.sdk.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClientProvider @Inject constructor() {
    private val logger = LoggerFactory.getLogger(KtorClientProvider::class.java)
//    private val baseUrl = "http://192.168.0.22:8080/api/v1"

    //    https://active-lively-pika.ngrok-free.app/api/v1/hello/
    private val baseUrl = "http://active-lively-pika.ngrok-free.app/api/v1"
    private var bearerTokenPrimary: String? = null
    private var bearerTokenSecondary: String? = null

    // Lista de URLs que não devem incluir o token Bearer
    private val noAuthUrls = listOf(
        "$baseUrl/session/create-session"
    )

    init {
        logger.info("Initializing KtorClientProvider")
    }

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
                    logger.info("Loading bearer tokens")
                    BearerTokens(bearerTokenPrimary ?: "", bearerTokenSecondary ?: "")
                }
                sendWithoutRequest { request ->
                    !noAuthUrls.contains(request.url.toString())
                }
            }
        }
        install(HttpRedirect) {
            checkHttpMethod =
                false
        }
        defaultRequest {
            headers {
                append("Content-Type", "application/json")
                append("Accept", "application/json")
            }
        }
        expectSuccess = false
    }

    fun getBaseUrl(): String {
        logger.info("Getting base URL: $baseUrl")
        return baseUrl
    }

    fun cleanBearerTokens() {
        logger.info("Clearing bearer tokens")
        bearerTokenPrimary = null
        bearerTokenSecondary = null
    }

    fun setBearerTokenPrimary(newToken: String) {
        logger.info("Setting primary bearer token")
        bearerTokenPrimary = newToken
    }

    fun setBearerTokenSecondary(newToken: String) {
        logger.info("Setting secondary bearer token")
        bearerTokenSecondary = newToken
    }

    fun isTokenAvailable(): Boolean {
        Log.d("KtorClientProvider", "Bearer token primary: $bearerTokenPrimary")
        return !bearerTokenPrimary.isNullOrEmpty()
    }
}

fun String.appendPath(path: String): String {
    val fullPath = if (this.endsWith("/")) {
        this + path.removePrefix("/")
    } else {
        "$this/$path"
    }
    Log.d("KtorClientProvider", "Full URL: $fullPath")
    return fullPath
}