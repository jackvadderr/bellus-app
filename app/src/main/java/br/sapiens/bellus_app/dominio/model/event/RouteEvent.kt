package br.sapiens.bellus_app.dominio.model.event

sealed class RouteEvent {
    data class UpdateRoute(val route: String) : RouteEvent()
}
