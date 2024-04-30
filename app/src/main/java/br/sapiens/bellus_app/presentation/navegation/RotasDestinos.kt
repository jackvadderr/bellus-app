package br.sapiens.bellus_app.presentation.navegation

sealed class RotasDestinos(val rota: String) {
    data object Splash : RotasDestinos("splash")
    data object Login : RotasDestinos("login")
    data object LoginCredencial : RotasDestinos("login-credencial")
    data object Register : RotasDestinos("register")
    data object Home : RotasDestinos("home")
    data object Perfil : RotasDestinos("perfil")
    data object Pesquisar : RotasDestinos("pesquisar")
}