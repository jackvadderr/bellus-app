package br.sapiens.bellus_app.utils

import io.github.cdimascio.dotenv.dotenv

object FornecedorCredenciais {
    private val dotenv = dotenv {directory = "/assets"; filename = "env"}
    val API_KEY_FIREBASE: String by lazy { dotenv["API_KEY_FIREBASE"] }
    val WEB_GOOGLE: String by lazy { dotenv["WEB_GOOGLE"] }
    val ANDROID_GOOGLE: String by lazy { dotenv["ANDROID_GOOGLE"] }
}