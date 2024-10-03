package br.sapiens.bellus_app.dominio.redux

import br.sapiens.bellus_app.dominio.model.state.AuthState
import br.sapiens.bellus_app.dominio.model.state.MarketplaceState
import br.sapiens.bellus_app.dominio.model.state.RouteState
import br.sapiens.bellus_app.dominio.model.state.UserProfileState

data class ApplicationState(
    val authState: AuthState = AuthState.Unauthenticated,
    val marketplaceState: MarketplaceState = MarketplaceState(),
    val routeState: RouteState = RouteState(),
    val userProfileState: UserProfileState = UserProfileState(),
)
