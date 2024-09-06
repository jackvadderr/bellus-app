package br.sapiens.bellus_app.dominio.redux

import br.sapiens.bellus_app.dominio.model.AuthState

data class ApplicationState(
    val authState: AuthState = AuthState.Unauthenticated
)