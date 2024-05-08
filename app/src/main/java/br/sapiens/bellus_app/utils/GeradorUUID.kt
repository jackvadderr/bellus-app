package br.sapiens.bellus_app.utils

import java.security.MessageDigest
import java.util.UUID

fun gerarUUID(): String {
    /*
     * Gera um UUID aleatório
     */
    return UUID.randomUUID().toString()
}

fun gerarToken(): String {
    /*
     * Gera um token aleatório a partir de um UUID
     */
    val rawToken = gerarUUID()
    val bytes = rawToken.toByteArray()
    val md  = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
    return hashedNonce
}