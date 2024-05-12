package br.sapiens.bellus_app.dominio.sdk

import com.google.firebase.auth.FirebaseAuth

class AuthService(private val firebaseAuth: FirebaseAuth) {

    val userId: String?
        get() = firebaseAuth.currentUser?.uid

    fun isUserLogin(): Boolean = firebaseAuth.currentUser != null

    fun refreshUserToken(onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }
}