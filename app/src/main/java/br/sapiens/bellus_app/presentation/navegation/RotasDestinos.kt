package br.sapiens.bellus_app.presentation.navegation

sealed class RotasDestinos(val rota: String) {
    data object Splash : RotasDestinos("splash")
    data object LoginSocial : RotasDestinos("login-social")
    data object LoginCredencial : RotasDestinos("login-credencial")
    data object Cadastro : RotasDestinos("cadastro")
    data object Home : RotasDestinos("home")
    data object Perfil : RotasDestinos("perfil")
    data object Pesquisar : RotasDestinos("pesquisar")
}