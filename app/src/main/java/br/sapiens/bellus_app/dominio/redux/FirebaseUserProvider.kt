package br.sapiens.bellus_app.dominio.redux

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseUserProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun getUserId(): String? = firebaseAuth.currentUser?.uid

    fun isUserAuthenticated(): Boolean = firebaseAuth.currentUser != null
}