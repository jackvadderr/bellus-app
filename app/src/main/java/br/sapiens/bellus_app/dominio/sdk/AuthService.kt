package br.sapiens.bellus_app.dominio.sdk

import com.google.firebase.auth.FirebaseAuth

class AuthService(private val firebaseAuth: FirebaseAuth) {

    val userId: String?
        get() = firebaseAuth.currentUser?.uid

    fun isUserLogin(): Boolean = firebaseAuth.currentUser != null

}