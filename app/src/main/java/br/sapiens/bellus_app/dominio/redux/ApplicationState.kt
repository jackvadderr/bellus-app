package br.sapiens.bellus_app.dominio.redux

import br.sapiens.bellus_app.dominio.model.AuthState
import br.sapiens.bellus_app.dominio.model.MarketplaceState

data class ApplicationState(
    val authState: AuthState = AuthState.Unauthenticated,
    val marketplaceState: MarketplaceState = MarketplaceState()
)