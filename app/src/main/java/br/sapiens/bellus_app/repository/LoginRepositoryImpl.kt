package br.sapiens.bellus_app.repository

import br.sapiens.bellus_app.utils.State
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

fun interface LoginRepositoryImpl {
    suspend fun loginWithCredential(authCredential: AuthCredential): State<FirebaseUser>
}